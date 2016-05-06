package com.lite.blackdream.framework.component;

import com.lite.blackdream.framework.model.BeanDefinition;
import com.lite.blackdream.framework.model.DirtyData;
import com.lite.blackdream.framework.model.Domain;
import com.lite.blackdream.framework.model.PropertyDefinition;
import com.lite.blackdream.framework.util.ConfigProperties;
import com.lite.blackdream.framework.util.FileUtil;
import com.lite.blackdream.framework.util.ReflectionUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.File;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author LaineyC
 */
public abstract class BaseRepository<E extends Domain, ID extends Serializable> implements Repository<E, ID> {

    private static final Log logger = LogFactory.getLog("error");

    //private static final Map<Class<? extends Domain>, LinkedBlockingDeque<? extends Domain>> cacheDataBase = new HashMap<>();

    //private static final Map<Class<? extends Domain>, LinkedBlockingDeque<DirtyData>> dirtyDataBase = new HashMap<>();

    protected LinkedBlockingDeque<E> entityCollection = new LinkedBlockingDeque<>();

    protected LinkedBlockingDeque<DirtyData> dirtyCollection = new LinkedBlockingDeque<>();

    protected Class<E> entityClass = ReflectionUtil.getSuperClassGenericsType(getClass(), 0);

    //protected Class<ID> idClass = ReflectionUtil.getSuperClassGenericsType(getClass(), 1);

    protected BeanDefinition beanDefinition = ReflectionUtil.getBeanDefinition(entityClass);

    @Autowired
    protected ElementConverter<E> elementConverter;

    public BaseRepository(){
        //cacheDataBase.put(entityClass, entityCollection);
        //dirtyDataBase.put(entityClass, dirtyCollection);
    }

    @Override
    public void insert(E entity) {
        try {
            entityCollection.putFirst(entity);
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }

        DirtyData<E> dirtyData = new DirtyData<>();
        dirtyData.setDirtyType(DirtyData.DirtyType.INSERT);
        dirtyData.setEntity(entity);

        try {
            dirtyCollection.putFirst(dirtyData);
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(E entity) {
        if(!entityCollection.contains(entity)){
            throw new RuntimeException("实体不存在");
        }
        entityCollection.remove(entity);

        DirtyData<E> dirtyData = new DirtyData<>();
        dirtyData.setDirtyType(DirtyData.DirtyType.DELETE);
        dirtyData.setEntity(entity);

        try {
            dirtyCollection.putFirst(dirtyData);
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(E entity) {
        if(!entityCollection.contains(entity)){
            throw new RuntimeException("实体不存在");
        }

        DirtyData<E> dirtyData = new DirtyData<>();
        dirtyData.setDirtyType(DirtyData.DirtyType.UPDATE);
        dirtyData.setEntity(entity);
        try {
            dirtyCollection.putFirst(dirtyData);
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public E selectById(ID id) {
        List<E> entityList = entityCollection.stream().filter(p -> p.getId().equals(id)).collect(Collectors.toList());
        if(entityList.size() > 1){
            throw new RuntimeException("获取到多个对象");
        }
        return entityList.isEmpty() ? null :entityList.get(0);
    }

    @Override
    public E selectOne(E entity) {
        List<E> entityList = selectList(entity);
        if(entityList.size() > 1){
            throw new RuntimeException("获取到多个对象");
        }
        return entityList.isEmpty() ? null :entityList.get(0);
    }

    @Override
    public List<E> selectList(E entity) {
        return entityCollection.stream().filter(domain -> {
            for (PropertyDefinition propertyDefinition : beanDefinition.getPropertyDefinitions()) {
                Object templatePropertyValue = propertyDefinition.invokeGetMethod(entity);
                Object domainPropertyValue = propertyDefinition.invokeGetMethod(domain);
                if(templatePropertyValue == null){
                    continue;
                }
                if(domainPropertyValue instanceof Collection){
                    continue;
                }
                if (!templatePropertyValue.equals(domainPropertyValue)) {
                    return false;
                }
            }
            return true;
        }).collect(Collectors.toList());
    }

    @Override
    public List<E> filter(Predicate<E> predicate) {
        return entityCollection.stream().filter(predicate).collect(Collectors.toList());
    }

    @Override
    public long count(E entity) {
        return entityCollection.stream().filter(domain -> {
            for (PropertyDefinition propertyDefinition : beanDefinition.getPropertyDefinitions()) {
                Object templatePropertyValue = propertyDefinition.invokeGetMethod(entity);
                Object domainPropertyValue = propertyDefinition.invokeGetMethod(domain);
                if(templatePropertyValue == null){
                    continue;
                }
                if(domainPropertyValue instanceof Collection){
                    continue;
                }
                if (!templatePropertyValue.equals(domainPropertyValue)) {
                    return false;
                }
            }
            return true;
        }).count();
    }

    @Override
    public boolean exists(E entity, ID id) {
        return entityCollection.stream().filter(domain -> {
            for (PropertyDefinition propertyDefinition : beanDefinition.getPropertyDefinitions()) {
                Object templatePropertyValue = propertyDefinition.invokeGetMethod(entity);
                Object domainPropertyValue = propertyDefinition.invokeGetMethod(domain);
                if(templatePropertyValue == null){
                    continue;
                }
                if(domainPropertyValue instanceof Collection){
                    continue;
                }
                if (domain.getId().equals(id) || !templatePropertyValue.equals(domainPropertyValue)) {
                    return false;
                }
            }
            return true;
        }).count() > 0;
    }

    /**
     * 初始化
     */
    @Override
    public void init(){
        File collectionFile = new File(ConfigProperties.DATABASE_PATH + ConfigProperties.fileSeparator + beanDefinition.getBeanName());
        if(!collectionFile.exists()){
            collectionFile.mkdirs();
        }
        this.read();
    }

    /**
     * 读取数据
     */
    @Override
    public void read(){
        File collectionFile = new File(ConfigProperties.DATABASE_PATH + ConfigProperties.fileSeparator + beanDefinition.getBeanName());
        File[] files = collectionFile.listFiles((dir, name) -> name.endsWith(".xml"));

        if(files != null) {
            for (File documentFile : files) {
                Long id = Long.valueOf(documentFile.getName().split("\\.")[0]);
                String filePath = ConfigProperties.DATABASE_PATH + ConfigProperties.fileSeparator + beanDefinition.getBeanName() + ConfigProperties.fileSeparator + id + ".xml";
                try {
                    Document document = FileUtil.readXml(filePath);
                    Element element = document.getRootElement();
                    E entity = elementConverter.fromElement(element);
                    entityCollection.putFirst(entity);
                }
                catch (Exception e){
                    logger.error("read:" + filePath, e);
                    throw new RuntimeException("read:" + filePath, e);
                }
            }
        }
    }

    /**
     * 删除数据
     */
    @Override
    public void remove(E entity){
        String filePath = ConfigProperties.DATABASE_PATH + ConfigProperties.fileSeparator + beanDefinition.getBeanName() + ConfigProperties.fileSeparator + entity.getId() + ".xml";
        File file = new File(filePath);
        if(!file.delete()){
            logger.error("remove:" + filePath);
            throw new RuntimeException("remove:" + file);
        }
    }

    /**
     * 写入数据
     */
    @Override
    public void write(E entity){
        Document document = DocumentHelper.createDocument();
        Element element = elementConverter.toElement(entity);
        document.add(element);
        String filePath = ConfigProperties.DATABASE_PATH + ConfigProperties.fileSeparator + beanDefinition.getBeanName() + ConfigProperties.fileSeparator + entity.getId() + ".xml";
        try {
            FileUtil.writeXml(document, filePath);
        }
        catch (Exception e){
            logger.error("write:" + filePath);
            throw new RuntimeException("write:" + filePath, e);
        }
    }

    /**
     * 同步数据的服务
     */
    @Override
    public void run() {
        while(true){
            DirtyData<E> dirtyData;
            try {
                dirtyData = dirtyCollection.takeLast();
            }
            catch (Exception e){
                throw new RuntimeException(e);
            }

            E entity = dirtyData.getEntity();
            DirtyData.DirtyType dirtyType = dirtyData.getDirtyType();

            if(DirtyData.DirtyType.DELETE.equals(dirtyType)){
                this.remove(entity);
            }
            else if(DirtyData.DirtyType.INSERT.equals(dirtyType)){
                this.write(entity);
            }
            else if(DirtyData.DirtyType.UPDATE.equals(dirtyType)){
                this.write(entity);
            }
        }
    }

}