package com.lite.blackdream.business.service;

import com.lite.blackdream.business.domain.*;
import com.lite.blackdream.business.domain.TemplateStrategy;
import com.lite.blackdream.business.domain.tag.Global;
import com.lite.blackdream.business.parameter.dynamicmodel.DynamicModelQueryRequest;
import com.lite.blackdream.business.parameter.generatorinstance.*;
import com.lite.blackdream.business.repository.*;
import com.lite.blackdream.framework.el.Context;
import com.lite.blackdream.framework.exception.AppException;
import com.lite.blackdream.framework.component.BaseService;
import com.lite.blackdream.framework.model.Authentication;
import com.lite.blackdream.framework.model.PagerResult;
import com.lite.blackdream.framework.util.ConfigProperties;
import com.lite.blackdream.framework.util.FileUtil;
import com.lite.blackdream.framework.util.ZipUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.io.File;
import java.util.*;

/**
 * @author LaineyC
 */
@Service
public class GeneratorInstanceServiceImpl extends BaseService implements GeneratorInstanceService{

    @Autowired
    private GeneratorInstanceRepository generatorInstanceRepository;

    @Autowired
    private GeneratorRepository generatorRepository;

    @Autowired
    private DataModelRepository dataModelRepository;

    @Autowired
    private TemplateStrategyRepository templateStrategyRepository;

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private DynamicModelService dynamicModelService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public GeneratorInstance create(GeneratorInstanceCreateRequest request) {
        Authentication authentication = request.getAuthentication();
        Long userId = authentication.getUserId();
        GeneratorInstance generatorInstance = new GeneratorInstance();
        generatorInstance.setId(idWorker.nextId());
        generatorInstance.setName(request.getName());
        generatorInstance.setIsDelete(false);
        Long generatorId = request.getGeneratorId();
        Generator generatorPersistence = generatorRepository.selectById(generatorId);
        if(generatorPersistence == null){
            throw new AppException("生成器不存在");
        }
        generatorInstance.setGenerator(generatorPersistence);
        User user = new User();
        user.setId(userId);
        generatorInstance.setUser(user);

        DataModel dataModel = new DataModel();
        dataModel.setId(idWorker.nextId());
        dataModel.setIsDelete(false);
        dataModel.setGeneratorInstance(generatorInstance);
        dataModelRepository.insert(dataModel);

        DataModel dm = new DataModel();
        dm.setId(dataModel.getId());
        generatorInstance.setDataModel(dm);

        generatorInstanceRepository.insert(generatorInstance);
        return generatorInstance;
    }

    @Override
    public GeneratorInstance delete(GeneratorInstanceDeleteRequest request) {
        return null;
    }

    @Override
    public GeneratorInstance get(GeneratorInstanceGetRequest request) {
        Long id = request.getId();
        GeneratorInstance generatorInstancePersistence = generatorInstanceRepository.selectById(id);
        if(generatorInstancePersistence == null){
            throw new AppException("实例不存在");
        }
        GeneratorInstance generatorInstance = new GeneratorInstance();
        generatorInstance.setId(generatorInstancePersistence.getId());
        generatorInstance.setName(generatorInstancePersistence.getName());
        generatorInstance.setIsDelete(generatorInstancePersistence.getIsDelete());
        generatorInstance.setTemplateStrategy(generatorInstancePersistence.getTemplateStrategy());

        Long generatorId = generatorInstancePersistence.getGenerator().getId();
        Generator generatorPersistence = generatorRepository.selectById(generatorId);
        if(generatorPersistence == null){
            throw new AppException("生成器不存在");
        }
        generatorInstance.setGenerator(generatorPersistence);

        generatorInstance.setDataModel(generatorInstancePersistence.getDataModel());
        return generatorInstance;
    }

    @Override
    public PagerResult<GeneratorInstance> search(GeneratorInstanceSearchRequest request) {
        Authentication authentication = request.getAuthentication();
        Long userId = authentication.getUserId();
        String name =  StringUtils.hasText(request.getName())  ? request.getName() : null;
        List<GeneratorInstance> records = generatorInstanceRepository.filter(generatorInstance -> {
            if(generatorInstance.getIsDelete()){
                return false;
            }
            if (name != null) {
                if (!generatorInstance.getName().contains(name)) {
                    return false;
                }
            }
            if (userId != null) {
                if (!userId.equals(generatorInstance.getUser().getId())) {
                    return false;
                }
            }
            return true;
        });
        Integer page = request.getPage();
        Integer pageSize = request.getPageSize();
        Integer fromIndex = (page - 1) * pageSize;
        Integer toIndex = fromIndex + pageSize > records.size() ? records.size() : fromIndex + pageSize;
        List<GeneratorInstance> limitRecords = records.subList(fromIndex, toIndex);
        List<GeneratorInstance> result = new ArrayList<>();
        for(GeneratorInstance g : limitRecords){
            GeneratorInstance generatorInstance = new GeneratorInstance();
            generatorInstance.setId(g.getId());
            generatorInstance.setName(g.getName());
            generatorInstance.setIsDelete(g.getIsDelete());

            Long generatorId = g.getGenerator().getId();
            Generator generatorPersistence = generatorRepository.selectById(generatorId);
            if(generatorPersistence == null){
                throw new AppException("生成器不存在");
            }
            generatorInstance.setGenerator(generatorPersistence);
            generatorInstance.setDataModel(g.getDataModel());
            result.add(generatorInstance);
        }
        return new PagerResult<>(result, (long)records.size());
    }

    @Override
    public GeneratorInstance update(GeneratorInstanceUpdateRequest request) {
        Long id = request.getId();
        GeneratorInstance generatorInstancePersistence = generatorInstanceRepository.selectById(id);
        if(generatorInstancePersistence == null){
            throw new AppException("实例不存在");
        }
        String name = request.getName();
        if(name != null){
            generatorInstancePersistence.setName(request.getName());
        }

        Long templateStrategyId = request.getTemplateStrategyId();
        if(templateStrategyId != null){
            TemplateStrategy templateStrategyPersistence = templateStrategyRepository.selectById(templateStrategyId);
            if(templateStrategyPersistence == null){
                throw new AppException("策略文件不存在");
            }
            generatorInstancePersistence.setTemplateStrategy(templateStrategyPersistence);
        }

        generatorInstanceRepository.update(generatorInstancePersistence);
        return generatorInstancePersistence;
    }

    @Override
    public Object run(GeneratorInstanceRunRequest request) {
        Authentication authentication = request.getAuthentication();
        Long id = request.getId();
        GeneratorInstance generatorInstance = generatorInstanceRepository.selectById(id);

        Long templateStrategyId = request.getTemplateStrategyId();
        TemplateStrategy templateStrategy = templateStrategyRepository.selectById(templateStrategyId);

        Long generatorId = generatorInstance.getGenerator().getId();
        Generator generator = generatorRepository.selectById(generatorId);

        DynamicModelQueryRequest dynamicModelQueryRequest = new DynamicModelQueryRequest();
        dynamicModelQueryRequest.setGeneratorId(generatorId);
        dynamicModelQueryRequest.setAuthentication(authentication);
        List<DynamicModel> dynamicModels = dynamicModelService.query(dynamicModelQueryRequest);
        Map<Long, DynamicModel> dynamicModelCache = new HashMap<>();
        Map<Long, Map<String,  Map<String, Set<String>>>> dynamicModelKeysCache = new HashMap<>();
        dynamicModels.forEach(dynamicModel -> {
            DynamicModel newDynamicModel = new DynamicModel();
            newDynamicModel.setId(dynamicModel.getId());
            newDynamicModel.setName(dynamicModel.getName());
            newDynamicModel.setIcon(dynamicModel.getIcon());

            Map<String, Map<String, Set<String>>> keysCache = new HashMap<>();
            Map<String, Set<String>> propertiesKeys = new HashMap<>();
            Set<String> propertiesKeys_dateTypeKeys = new HashSet<>();
            Set<String> propertiesKeys_dataModelTypeKeys = new HashSet<>();
            propertiesKeys.put("dateTypeKeys", propertiesKeys_dateTypeKeys);
            propertiesKeys.put("dataModelTypeKeys", propertiesKeys_dataModelTypeKeys);
            Map<String, Set<String>> associationKeys = new HashMap<>();
            Set<String> associationKeys_dateTypeKeys = new HashSet<>();
            Set<String> associationKeys_dataModelTypeKeys = new HashSet<>();
            associationKeys.put("dateTypeKeys", associationKeys_dateTypeKeys);
            associationKeys.put("dataModelTypeKeys", associationKeys_dataModelTypeKeys);
            keysCache.put("propertiesKeys", propertiesKeys);
            keysCache.put("associationKeys", associationKeys);
            dynamicModelKeysCache.put(dynamicModel.getId(), keysCache);

            List<DynamicProperty> properties = dynamicModel.getProperties();
            properties.forEach(property -> {
                if("Date".equals(property.getType())){
                    propertiesKeys_dateTypeKeys.add(property.getName());
                }
                else if("Model".equals(property.getType())){
                    propertiesKeys_dataModelTypeKeys.add(property.getName());
                }

                DynamicProperty newDynamicProperty = new DynamicProperty();
                newDynamicProperty.setId(property.getId());
                newDynamicProperty.setLabel(property.getLabel());
                newDynamicProperty.setName(property.getName());
                newDynamicProperty.setViewWidth(property.getViewWidth());
                newDynamicProperty.setType(property.getType());
                newDynamicProperty.setDefaultValue(property.getDefaultValue());
                newDynamicModel.getProperties().add(newDynamicProperty);
            });
            List<DynamicProperty> association = dynamicModel.getAssociation();
            association.forEach(property -> {
                if("Date".equals(property.getType())){
                    associationKeys_dateTypeKeys.add(property.getName());
                }
                else if("Model".equals(property.getType())){
                    associationKeys_dataModelTypeKeys.add(property.getName());
                }

                DynamicProperty newDynamicProperty = new DynamicProperty();
                newDynamicProperty.setId(property.getId());
                newDynamicProperty.setLabel(property.getLabel());
                newDynamicProperty.setName(property.getName());
                newDynamicProperty.setViewWidth(property.getViewWidth());
                newDynamicProperty.setType(property.getType());
                newDynamicProperty.setDefaultValue(property.getDefaultValue());
                newDynamicModel.getAssociation().add(newDynamicProperty);
            });
            dynamicModelCache.put(newDynamicModel.getId(), newDynamicModel);
        });

        Long dataModelId = generatorInstance.getDataModel().getId();
        DataModel rootDataModel = dataModelRepository.selectById(dataModelId);
        Map<Long, DataModel> dataModelSourceCache = new HashMap<>();
        Map<Long, DataModel> dataModelTargetCache = new HashMap<>();
        LinkedList<DataModel> stack = new LinkedList<>();
        stack.push(rootDataModel);
        while (!stack.isEmpty()) {
            DataModel dataModel = stack.pop();
            dataModelSourceCache.put(dataModel.getId(), dataModel);
            dataModelTargetCache.put(dataModel.getId(), new DataModel());
            dataModel.getChildren().forEach(stack :: push);
        }
        dataModelSourceCache.forEach((dataModelSourceId, dataModelSource) -> {
            DataModel dataModelTarget = dataModelTargetCache.get(dataModelSourceId);
            dataModelTarget.setId(dataModelSource.getId());
            dataModelTarget.setName(dataModelSource.getName());
            if (dataModelSource.getParent() != null) {
                Long parentId = dataModelSource.getParent().getId();
                DataModel parentSource = dataModelSourceCache.get(parentId);
                if (parentSource != null) {
                    DataModel parentTarget = dataModelTargetCache.get(parentId);
                    dataModelTarget.setParent(parentTarget);
                }
            }
            dataModelSource.getChildren().forEach(child -> dataModelTarget.getChildren().add(dataModelTargetCache.get(child.getId())));

            if (!dataModelSource.equals(rootDataModel)) {
                DynamicModel dynamicModel = dynamicModelCache.get(dataModelSource.getDynamicModel().getId());
                dataModelTarget.setDynamicModel(dynamicModel);

                Map<String, Map<String, Set<String>>> keysCache = dynamicModelKeysCache.get(dynamicModel.getId());
                Map<String, Set<String>> propertiesKeys = keysCache.get("propertiesKeys");
                Map<String, Set<String>> associationKeys = keysCache.get("associationKeys");
                Set<String> propertiesKeys_dateTypeKeys = propertiesKeys.get("dateTypeKeys");
                Set<String> propertiesKeys_dataModelTypeKeys = propertiesKeys.get("dataModelTypeKeys");
                Set<String> associationKeys_dateTypeKeys = associationKeys.get("dateTypeKeys");
                Set<String> associationKeys_dataModelTypeKeys = associationKeys.get("dataModelTypeKeys");
                dataModelSource.getProperties().forEach((name, value) -> {
                    if (propertiesKeys_dateTypeKeys.contains(name)) {
                        dataModelTarget.getProperties().put(name, new Date((Long) value));
                    }
                    else if (propertiesKeys_dataModelTypeKeys.contains(name)) {
                        dataModelTarget.getProperties().put(name, dataModelTargetCache.get(value));
                    }
                    else {
                        dataModelTarget.getProperties().put(name, value);
                    }
                });
                dataModelSource.getAssociation().forEach(property -> {
                    Map<String, Object> newProperty = new LinkedHashMap<>();
                    property.forEach((name, value) -> {
                        if (associationKeys_dateTypeKeys.contains(name)) {
                            newProperty.put(name, new Date((Long) value));
                        }
                        else if (associationKeys_dataModelTypeKeys.contains(name)) {
                            newProperty.put(name, dataModelTargetCache.get(value));
                        }
                        else {
                            newProperty.put(name, value);
                        }
                    });
                    dataModelTarget.getAssociation().add(newProperty);
                });
            }
        });

        Template templateTemplate = new Template();
        templateTemplate.setIsDelete(false);
        templateTemplate.setGenerator(generator);
        List<Template> templates = templateRepository.selectList(templateTemplate);
        Map<Long, Template> templateCache = new HashMap<>();
        templates.forEach(template -> {
            Template newTemplate = new Template();
            newTemplate.setId(template.getId());
            newTemplate.setName(template.getName());
            newTemplate.setUrl(template.getUrl());
            templateCache.put(template.getId(), newTemplate);
        });

        Long generateId = idWorker.nextId();
        User developer = userRepository.selectById(generator.getDeveloper().getId());
        TemplateStrategy templateStrategyClone = templateStrategy.clone();

        Global global = new Global();
        global.setGenerateId(generateId);
        global.setTemplateCache(templateCache);

        User userClone = new User();
        userClone.setId(authentication.getUserId());
        userClone.setUserName(authentication.getUserName());
        global.setUser(userClone);

        Generator generatorClone = new Generator();
        generatorClone.setId(generator.getId());
        generatorClone.setName(generator.getName());
        global.setGenerator(generatorClone);

        GeneratorInstance generatorInstanceClone = new GeneratorInstance();
        generatorInstanceClone.setId(generatorInstance.getId());
        generatorInstanceClone.setName(generatorInstance.getName());
        global.setGeneratorInstance(generatorInstanceClone);

        User developerClone = new User();
        developerClone.setId(developer.getId());
        developerClone.setUserName(developer.getUserName());
        global.setDeveloper(developerClone);

        global.setTemplateStrategy(templateStrategyClone);

        Context context = new Context();
        context.setVariable("global", global);
        context.setVariable("data", dataModelTargetCache.get(rootDataModel.getId()));
        try {
            templateStrategyClone.execute(context);
        }
        catch (Throwable e){
            List<String> messageList = new ArrayList<>();
            LinkedList<Throwable> exceptionStack = new LinkedList<>();
            exceptionStack.push(e);
            while (!exceptionStack.isEmpty()) {
                Throwable exception = exceptionStack.pop();
                messageList.add(exception.toString());
                if(exception.getCause() != null){
                    exceptionStack.push(exception.getCause());
                }
            }
            return messageList;
        }
        String generatePath = ConfigProperties.CODEBASE_PATH + ConfigProperties.fileSeparator + authentication.getUserId() + ConfigProperties.fileSeparator + generator.getName() + "(" + generateId + ")";
        File generateFolder = new File(generatePath);
        try {
            ZipUtils.compress(generateFolder);
        }
        catch (Exception e){
            throw new AppException(e,"压缩代码失败");
        }
        FileUtil.deleteFile(generateFolder);
        return "/Codebase/" + authentication.getUserId() + "/" + generator.getName() + "(" + generateId + ").zip";
    }

}
