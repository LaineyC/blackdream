package com.lite.blackdream.business.service;

import com.lite.blackdream.business.domain.Generator;
import com.lite.blackdream.business.domain.TemplateStrategy;
import com.lite.blackdream.business.domain.tag.*;
import com.lite.blackdream.business.domain.tag.Set;
import com.lite.blackdream.business.parameter.templatestrategy.*;
import com.lite.blackdream.business.repository.GeneratorRepository;
import com.lite.blackdream.business.repository.TemplateStrategyRepository;
import com.lite.blackdream.framework.exception.AppException;
import com.lite.blackdream.framework.component.BaseService;
import com.lite.blackdream.framework.model.Authentication;
import com.lite.blackdream.framework.model.BeanDefinition;
import com.lite.blackdream.framework.model.Domain;
import com.lite.blackdream.framework.model.PagerResult;
import com.lite.blackdream.framework.util.ReflectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * @author LaineyC
 */
@Service
public class TemplateStrategyServiceImpl extends BaseService implements TemplateStrategyService{

    private static final Map<String, TagMapConverter> tagMapConverters = new HashMap<>();

    private interface TagMapConverter<E extends Tag>{

         E fromMap(Map<String, Object> map);

    }

    private static class BaseTagMapConverter<E extends Tag> implements TagMapConverter<E>{

        protected Class<E> entityClass = ReflectionUtil.getSuperClassGenericsType(getClass(), 0);

        protected BeanDefinition beanDefinition = ReflectionUtil.getBeanDefinition(entityClass);

        public E fromMap(Map<String, Object> map){
            E entity;
            try {
                entity = entityClass.newInstance();
            }
            catch (Exception e){
                throw new RuntimeException();
            }
            beanDefinition.getPropertyDefinitions().forEach(propertyDefinition -> {
                String propertyName = propertyDefinition.getPropertyName();
                Class<?> propertyType = propertyDefinition.getPropertyType();
                if(!"tagName".equals(propertyName)) {
                    if (String.class.isAssignableFrom(propertyType)) {
                        String attributeValue = (String)map.get(propertyName);
                        if (attributeValue != null) {
                            propertyDefinition.invokeSetMethod(entity, attributeValue);
                        }
                    }
                    else if (Domain.class.isAssignableFrom(propertyType)) {
                        Object attributeValue = map.get(propertyName);
                        if (attributeValue != null) {
                            try {
                                Domain domainField = (Domain)propertyType.newInstance();
                                domainField.setId(Long.valueOf(attributeValue.toString()));
                                propertyDefinition.invokeSetMethod(entity, domainField);
                            }
                            catch (Exception e){
                                //
                            }
                        }
                    }
                    else if ("children".equals(propertyName)) {
                        List<Map<String, Object>> children = (List<Map<String, Object>>)map.get(propertyName);
                        if(children != null){
                            children.forEach(childMap -> {
                                String childName = (String)childMap.get("tagName");
                                TagMapConverter childTagMapConverter = tagMapConverters.get(childName);
                                Tag child = childTagMapConverter.fromMap(childMap);
                                entity.getChildren().add(child);
                            });
                        }
                    }
                    else if (!Collection.class.isAssignableFrom(propertyType)) {
                        Object attributeValue = map.get(propertyName);
                        if (attributeValue != null) {
                            try {
                                Object fieldValue = propertyType.getDeclaredConstructor(String.class).newInstance(attributeValue);
                                propertyDefinition.invokeSetMethod(entity, fieldValue);
                            }
                            catch (Exception e) {
                                //
                            }
                        }
                    }
                }
            });
            return entity;
        }

    }

    private static class BreakTagMapConverter extends BaseTagMapConverter<Break>{}
    private static class CallTagMapConverter extends BaseTagMapConverter<Call>{

        public Call fromMap(Map<String, Object> map){
            Call entity = new Call();

            String function = (String)map.get("function");
            if(function != null){
                entity.setFunction(function);
            }

            List<Map<String, Object>> children = (List<Map<String, Object>>)map.get("children");
            if(children != null){
                children.forEach(childMap -> {
                    String childName = (String) childMap.get("tagName");
                    TagMapConverter childTagMapConverter = tagMapConverters.get(childName);
                    Tag child = childTagMapConverter.fromMap(childMap);
                    entity.getChildren().add(child);
                });
            }

            String argument;
            int i = 1;
            List<String> arguments = new ArrayList<>();
            while((argument = (String)map.get("argument" + i)) != null){
                arguments.add(argument);
                i++;
            }
            entity.setArguments(arguments.toArray(new String[arguments.size()]));

            return entity;
        }

    }
    private static class ContinueTagMapConverter extends BaseTagMapConverter<Continue>{}
    private static class FileTagMapConverter extends BaseTagMapConverter<File>{}
    private static class FolderTagMapConverter extends BaseTagMapConverter<Folder>{}
    private static class ForeachTagMapConverter extends BaseTagMapConverter<Foreach>{}
    private static class FunctionTagMapConverter extends BaseTagMapConverter<Function>{

        public Function fromMap(Map<String, Object> map){
            Function entity = new Function();

            String name = (String)map.get("name");
            if(name != null){
                entity.setName(name);
            }

            List<Map<String, Object>> children = (List<Map<String, Object>>)map.get("children");
            if(children != null){
                children.forEach(childMap -> {
                    String childName = (String) childMap.get("tagName");
                    TagMapConverter childTagMapConverter = tagMapConverters.get(childName);
                    Tag child = childTagMapConverter.fromMap(childMap);
                    entity.getChildren().add(child);
                });
            }

            String argument;
            int i = 1;
            List<String> arguments = new ArrayList<>();
            while((argument = (String)map.get("argument" + i)) != null){
                arguments.add(argument);
                i++;
            }
            entity.setArguments(arguments.toArray(new String[arguments.size()]));

            return entity;
        }

    }
    private static class IfTagMapConverter extends BaseTagMapConverter<If>{}
    private static class ReturnTagConverter extends BaseTagMapConverter<Return>{}
    private static class SetTagMapConverter extends BaseTagMapConverter<Set>{}
    private static class TemplateContextTagMapConverter extends BaseTagMapConverter<TemplateContext>{}
    private static class VarTagMapConverter extends BaseTagMapConverter<Var>{}

    static{
        tagMapConverters.put(Break.class.getSimpleName(), new BreakTagMapConverter());
        tagMapConverters.put(Call.class.getSimpleName(), new CallTagMapConverter());
        tagMapConverters.put(Continue.class.getSimpleName(), new ContinueTagMapConverter());
        tagMapConverters.put(File.class.getSimpleName(), new FileTagMapConverter());
        tagMapConverters.put(Folder.class.getSimpleName(), new FolderTagMapConverter());
        tagMapConverters.put(Foreach.class.getSimpleName(), new ForeachTagMapConverter());
        tagMapConverters.put(Function.class.getSimpleName(), new FunctionTagMapConverter());
        tagMapConverters.put(If.class.getSimpleName(), new IfTagMapConverter());
        tagMapConverters.put(Return.class.getSimpleName(), new ReturnTagConverter());
        tagMapConverters.put(Set.class.getSimpleName(), new SetTagMapConverter());
        tagMapConverters.put(TemplateContext.class.getSimpleName(), new TemplateContextTagMapConverter());
        tagMapConverters.put(Var.class.getSimpleName(), new VarTagMapConverter());
    }

    @Autowired
    private TemplateStrategyRepository templateStrategyRepository;

    @Autowired
    private GeneratorRepository generatorRepository;

    @Override
    public TemplateStrategy create(TemplateStrategyCreateRequest request) {
        TemplateStrategy templateStrategy = new TemplateStrategy();
        templateStrategy.setId(idWorker.nextId());
        templateStrategy.setName(request.getName());
        templateStrategy.setIsDelete(false);
        Long generatorId = request.getGeneratorId();
        Generator generatorPersistence = generatorRepository.selectById(generatorId);
        if(generatorPersistence == null){
            throw new AppException("生成器不存在");
        }
        templateStrategy.setGenerator(generatorPersistence);

        request.getChildren().forEach(childMap -> {
            String childName = (String) childMap.get("tagName");
            TagMapConverter childTagMapConverter = tagMapConverters.get(childName);
            Tag child = childTagMapConverter.fromMap(childMap);
            templateStrategy.getChildren().add(child);
        });

        templateStrategyRepository.insert(templateStrategy);
        return templateStrategy;
    }

    @Override
    public TemplateStrategy delete(TemplateStrategyDeleteRequest request) {
        throw new AppException("未开放");
    }

    @Override
    public TemplateStrategy get(TemplateStrategyGetRequest request) {
        Long id = request.getId();
        TemplateStrategy templateStrategyPersistence = templateStrategyRepository.selectById(id);
        if(templateStrategyPersistence == null){
            throw new AppException("策略文件不存在");
        }
        TemplateStrategy templateStrategy = new TemplateStrategy();
        templateStrategy.setId(templateStrategyPersistence.getId());
        templateStrategy.setName(templateStrategyPersistence.getName());
        templateStrategy.setIsDelete(templateStrategyPersistence.getIsDelete());
        Long generatorId = templateStrategyPersistence.getGenerator().getId();
        Generator generatorPersistence = generatorRepository.selectById(generatorId);
        if(generatorPersistence == null){
            throw new AppException("生成器不存在");
        }
        templateStrategy.setGenerator(generatorPersistence);
        templateStrategy.setChildren(templateStrategyPersistence.getChildren());
        return templateStrategy;
    }

    @Override
    public List<TemplateStrategy> query(TemplateStrategyQueryRequest request) {
        TemplateStrategy templateStrategyTemplate = new TemplateStrategy();
        templateStrategyTemplate.setIsDelete(false);
        Long generatorId = request.getGeneratorId();
        Generator generator = new Generator();
        generator.setId(generatorId);
        templateStrategyTemplate.setGenerator(generator);

        List<TemplateStrategy> records = templateStrategyRepository.selectList(templateStrategyTemplate);
        List<TemplateStrategy> result = new ArrayList<>();
        for(TemplateStrategy t : records){
            TemplateStrategy templateStrategy = new TemplateStrategy();
            templateStrategy.setId(t.getId());
            templateStrategy.setName(t.getName());
            templateStrategy.setIsDelete(t.getIsDelete());
            Generator generatorPersistence = generatorRepository.selectById(generatorId);
            if(generatorPersistence == null){
                throw new AppException("生成器不存在");
            }
            templateStrategy.setGenerator(generatorPersistence);
            templateStrategy.setChildren(t.getChildren());
            result.add(templateStrategy);
        }
        return result;
    }

    @Override
    public PagerResult<TemplateStrategy> search(TemplateStrategySearchRequest request) {
        TemplateStrategy templateStrategyTemplate = new TemplateStrategy();
        templateStrategyTemplate.setIsDelete(false);
        Long generatorId = request.getGeneratorId();
        Generator generator = new Generator();
        generator.setId(generatorId);
        templateStrategyTemplate.setGenerator(generator);

        List<TemplateStrategy> records = templateStrategyRepository.selectList(templateStrategyTemplate);
        Integer page = request.getPage();
        Integer pageSize = request.getPageSize();
        Integer fromIndex = (page - 1) * pageSize;
        Integer toIndex = fromIndex + pageSize > records.size() ? records.size() : fromIndex + pageSize;
        List<TemplateStrategy> limitRecords = records.subList(fromIndex, toIndex);
        List<TemplateStrategy> result = new ArrayList<>();
        for(TemplateStrategy t : limitRecords){
            TemplateStrategy templateStrategy = new TemplateStrategy();
            templateStrategy.setId(t.getId());
            templateStrategy.setName(t.getName());
            templateStrategy.setIsDelete(t.getIsDelete());
            Generator generatorPersistence = generatorRepository.selectById(generatorId);
            if(generatorPersistence == null){
                throw new AppException("生成器不存在");
            }
            templateStrategy.setGenerator(generatorPersistence);
            templateStrategy.setChildren(t.getChildren());
            result.add(templateStrategy);
        }
        return new PagerResult<>(result, (long)records.size());
    }

    @Override
    public TemplateStrategy update(TemplateStrategyUpdateRequest request) {
        Long id = request.getId();
        TemplateStrategy templateStrategyPersistence = templateStrategyRepository.selectById(id);
        if(templateStrategyPersistence == null){
            throw new AppException("策略文件不存在");
        }

        Authentication authentication = request.getAuthentication();
        Long userId = authentication.getUserId();
        Generator generatorPersistence = generatorRepository.selectById(templateStrategyPersistence.getGenerator().getId());
        if(!userId.equals(generatorPersistence.getDeveloper().getId())){
            throw new AppException("权限不足");
        }

        templateStrategyPersistence.setName(request.getName());

        templateStrategyPersistence.getChildren().clear();
        request.getChildren().forEach(childMap -> {
            String childName = (String) childMap.get("tagName");
            TagMapConverter childTagMapConverter = tagMapConverters.get(childName);
            Tag child = childTagMapConverter.fromMap(childMap);
            templateStrategyPersistence.getChildren().add(child);
        });

        templateStrategyRepository.update(templateStrategyPersistence);
        return templateStrategyPersistence;
    }

}