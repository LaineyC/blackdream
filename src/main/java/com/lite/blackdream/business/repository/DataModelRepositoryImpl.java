package com.lite.blackdream.business.repository;

import com.lite.blackdream.business.domain.DataModel;
import com.lite.blackdream.framework.component.BaseRepository;
import com.lite.blackdream.framework.model.DirtyData;
import com.lite.blackdream.framework.util.ConfigProperties;
import com.lite.blackdream.framework.util.FileUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Repository;
import java.io.File;
import java.util.*;

/**
 * @author LaineyC
 */
@Repository
public class DataModelRepositoryImpl extends BaseRepository<DataModel, Long> implements DataModelRepository {

    private static final Log logger = LogFactory.getLog("error");

    @Override
    public void insert(DataModel entity, DataModel root) {
        Long parentId = entity.getParent().getId();

        LinkedList<DataModel> stack = new LinkedList<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            DataModel parent = stack.pop();
            if(parent.getId().equals(parentId)){
                parent.getChildren().add(entity);
                break;
            }
            parent.getChildren().forEach(stack :: push);
        }

        DirtyData<DataModel> dirtyData = new DirtyData<>();
        dirtyData.setDirtyType(DirtyData.DirtyType.INSERT);
        dirtyData.setEntity(entity);

        try {
            dirtyCollection.putFirst(dirtyData);
        }
        catch (Exception e){
            throw new RuntimeException("");
        }
    }

    @Override
    public void delete(DataModel entity, DataModel root) {
        Long parentId = entity.getParent().getId();

        LinkedList<DataModel> stack = new LinkedList<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            DataModel parent = stack.pop();
            if(parent.getId().equals(parentId)){
                parent.getChildren().removeIf(dataModel -> dataModel.getId().equals(entity.getId()));
                break;
            }
            parent.getChildren().forEach(stack :: push);
        }

        DirtyData<DataModel> dirtyData = new DirtyData<>();
        dirtyData.setDirtyType(DirtyData.DirtyType.DELETE);
        dirtyData.setEntity(entity);

        try {
            dirtyCollection.putFirst(dirtyData);
        }
        catch (Exception e){
            throw new RuntimeException("");
        }
    }

    @Override
    public void update(DataModel entity) {
/*
        if(!entityCollection.contains(entity)){
            throw new RuntimeException("");
        }
*/
        DirtyData<DataModel> dirtyData = new DirtyData<>();
        dirtyData.setDirtyType(DirtyData.DirtyType.UPDATE);
        dirtyData.setEntity(entity);
        try {
            dirtyCollection.putFirst(dirtyData);
        }
        catch (Exception e){
            throw new RuntimeException("");
        }
    }

    @Override
    public DataModel selectById(Long id, DataModel root){
        LinkedList<DataModel> stack = new LinkedList<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            DataModel dataModel = stack.pop();
            if(dataModel.getId().equals(id)){
                return dataModel;
            }
            dataModel.getChildren().forEach(stack :: push);
        }
        return null;
    }

    /**
     * 读取数据
     */
    @Override
    public void read(){
        File collectionFile = new File(ConfigProperties.DATABASE_PATH + ConfigProperties.fileSeparator + beanDefinition.getBeanName());
        File[] generatorInstanceFiles = collectionFile.listFiles(file -> file.isDirectory());
        if(generatorInstanceFiles != null){
            for(File generatorInstanceFile : generatorInstanceFiles){
                String generatorInstanceFolderName = generatorInstanceFile.getName();
                File[] dataModelFiles = generatorInstanceFile.listFiles((dir, name) -> name.endsWith(".xml"));
                if(dataModelFiles != null){
                    DataModel root = null;
                    Map<Long, DataModel> descendants = new LinkedHashMap<>();
                    for (File dataModelFile : dataModelFiles) {
                        Long id = Long.valueOf(dataModelFile.getName().split("\\.")[0]);
                        String filePath = ConfigProperties.DATABASE_PATH + ConfigProperties.fileSeparator + beanDefinition.getBeanName() + ConfigProperties.fileSeparator + generatorInstanceFolderName + ConfigProperties.fileSeparator + id + ".xml";
                        try {
                            Document document = FileUtil.readXml(filePath);
                            Element element = document.getRootElement();
                            DataModel entity = elementConverter.fromElement(element);
                            if(entity.getParent() == null){
                                root = entity;
                            }
                            else{
                                descendants.put(entity.getId(), entity);
                            }
                        }
                        catch (Exception e){
                            logger.error("read:" + filePath, e);
                            throw new RuntimeException("read:" + filePath, e);
                        }
                    }
                    if(root == null){
                        continue;
                    }
                    descendants.put(root.getId(), root);
                    descendants.forEach((id, dataModel) ->{
                        if(dataModel.getParent() != null){
                            Long parentId = dataModel.getParent().getId();
                            DataModel parent = descendants.get(parentId);
                            if(parent != null){
                                parent.getChildren().add(dataModel);
                            }
                        }
                    });
                    try {
                        entityCollection.putFirst(root);
                    }
                    catch (Exception e){
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    /**
     * 删除数据
     */
    @Override
    public void remove(DataModel entity){
        Long generatorInstanceId = entity.getGeneratorInstance().getId();
        String filePath = ConfigProperties.DATABASE_PATH + ConfigProperties.fileSeparator + beanDefinition.getBeanName() + ConfigProperties.fileSeparator + generatorInstanceId.toString() + ConfigProperties.fileSeparator + entity.getId() + ".xml";
        File file = new File(filePath);
        if(!file.delete()){
            logger.error("remove:" + filePath);
            throw new RuntimeException("remove:" + filePath);
        }
    }

    /**
     * 写入数据
     */
    @Override
    public void write(DataModel entity){
        Document document = DocumentHelper.createDocument();
        Element element = elementConverter.toElement(entity);
        document.add(element);
        Long generatorInstanceId = entity.getGeneratorInstance().getId();
        String filePath = ConfigProperties.DATABASE_PATH + ConfigProperties.fileSeparator + beanDefinition.getBeanName() + ConfigProperties.fileSeparator + generatorInstanceId.toString() + ConfigProperties.fileSeparator + entity.getId() + ".xml";
        try {
            File generatorInstanceFile = new File(ConfigProperties.DATABASE_PATH + ConfigProperties.fileSeparator + beanDefinition.getBeanName() + ConfigProperties.fileSeparator + generatorInstanceId.toString());
            if(!generatorInstanceFile.exists()){
                generatorInstanceFile.mkdirs();
            }
            FileUtil.writeXml(document, filePath);
        }
        catch (Exception e){
            logger.error("write:" + filePath);
            throw new RuntimeException("write:" + filePath, e);
        }
    }

}
