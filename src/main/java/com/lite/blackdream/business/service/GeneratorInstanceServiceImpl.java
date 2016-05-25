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
import com.lite.blackdream.framework.util.VelocityUtil;
import com.lite.blackdream.framework.util.ZipUtil;
import org.apache.velocity.tools.generic.ComparisonDateTool;
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
        Long userId = request.getAuthentication().getUserId();

        Long generatorId = request.getGeneratorId();
        Generator generatorPersistence = generatorRepository.selectById(generatorId);
        if(generatorPersistence == null){
            throw new AppException("生成器不存在");
        }

        if(!generatorPersistence.getIsApplied() && !generatorPersistence.getDeveloper().getId().equals(userId)){
            generatorPersistence.setIsApplied(true);
        }
        generatorPersistence.setInstanceCount(generatorPersistence.getInstanceCount() + 1);
        generatorRepository.update(generatorPersistence);

        GeneratorInstance generatorInstance = new GeneratorInstance();
        generatorInstance.setId(idWorker.nextId());
        generatorInstance.setName(request.getName());
        generatorInstance.setModifyDate(new Date());
        generatorInstance.setIsDelete(false);
        Generator generator = new Generator();
        generator.setId(generatorPersistence.getId());
        generatorInstance.setGenerator(generator);
        User user = new User();
        user.setId(userId);
        generatorInstance.setUser(user);
        generatorInstance.setVersion(generatorPersistence.getVersion());

        DataModel dataModel = new DataModel();
        dataModel.setId(idWorker.nextId());
        dataModel.setModifyDate(new Date());
        dataModel.setIsDelete(false);
        GeneratorInstance g = new GeneratorInstance();
        g.setId(generatorInstance.getId());
        dataModel.setGeneratorInstance(g);
        generator.setId(generatorPersistence.getId());
        dataModel.setGenerator(generator);
        dataModel.setUser(user);
        dataModelRepository.insert(dataModel);

        DataModel dm = new DataModel();
        dm.setId(dataModel.getId());
        generatorInstance.setDataModel(dm);
        generatorInstanceRepository.insert(generatorInstance);

        return generatorInstance;
    }

    @Override
    public GeneratorInstance delete(GeneratorInstanceDeleteRequest request) {
        Long id = request.getId();
        GeneratorInstance generatorInstancePersistence = generatorInstanceRepository.selectById(id);
        if(generatorInstancePersistence == null){
            throw new AppException("实例不存在");
        }

        Long userId = request.getAuthentication().getUserId();
        if(!userId.equals(generatorInstancePersistence.getUser().getId())){
            throw new AppException("权限不足");
        }
        generatorInstanceRepository.delete(generatorInstancePersistence);

        Long rootDataModelId = generatorInstancePersistence.getDataModel().getId();
        DataModel rootDataModePersistence = dataModelRepository.selectById(rootDataModelId);
        if(rootDataModePersistence == null){
            throw new AppException("rootDataMode不存在");
        }
        dataModelRepository.delete(rootDataModePersistence);

        Generator generatorPersistence = generatorRepository.selectById(generatorInstancePersistence.getGenerator().getId());
        generatorPersistence.setInstanceCount(generatorPersistence.getInstanceCount() - 1);
        generatorRepository.update(generatorPersistence);

        return generatorInstancePersistence;
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
        generatorInstance.setModifyDate(generatorInstancePersistence.getModifyDate());
        generatorInstance.setIsDelete(generatorInstancePersistence.getIsDelete());

        User userPersistence = userRepository.selectById(generatorInstancePersistence.getUser().getId());
        generatorInstance.setUser(userPersistence);

        Long generatorId = generatorInstancePersistence.getGenerator().getId();
        Generator generatorPersistence = generatorRepository.selectById(generatorId);
        if(generatorPersistence == null){
            throw new AppException("生成器不存在");
        }
        generatorInstance.setGenerator(generatorPersistence);

        generatorInstance.setDataModel(generatorInstancePersistence.getDataModel());
        generatorInstance.setVersion(generatorInstancePersistence.getVersion());

        return generatorInstance;
    }

    @Override
    public PagerResult<GeneratorInstance> search(GeneratorInstanceSearchRequest request) {
        Long userId = request.getUserId();
        String name = StringUtils.hasText(request.getName())  ? request.getName() : null;
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
            generatorInstance.setModifyDate(g.getModifyDate());
            generatorInstance.setIsDelete(g.getIsDelete());
            User userPersistence = userRepository.selectById(g.getUser().getId());
            generatorInstance.setUser(userPersistence);
            Generator generatorPersistence = generatorRepository.selectById(g.getGenerator().getId());
            if(generatorPersistence == null){
                throw new AppException("生成器不存在");
            }
            generatorInstance.setGenerator(generatorPersistence);
            generatorInstance.setDataModel(g.getDataModel());
            generatorInstance.setVersion(g.getVersion());
            result.add(generatorInstance);
        }

        String sortField = request.getSortField();
        String sortDirection = request.getSortDirection();
        if("modifyDate".equals(sortField)){
            if("DESC".equalsIgnoreCase(sortDirection)){
                result.sort((g1, g2) -> (int)(g2.getModifyDate().getTime() - g1.getModifyDate().getTime()));
            }
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

        Long userId = request.getAuthentication().getUserId();
        if(!userId.equals(generatorInstancePersistence.getUser().getId())){
            throw new AppException("权限不足");
        }

        String name = request.getName();
        if(name != null){
            generatorInstancePersistence.setName(request.getName());
        }

        generatorInstancePersistence.setModifyDate(new Date());
        generatorInstanceRepository.update(generatorInstancePersistence);

        return generatorInstancePersistence;
    }

    @Override
    public RunResult run(GeneratorInstanceRunRequest request) {
        RunResult runResult = new RunResult();
        Authentication authentication = request.getAuthentication();
        Long userId = authentication.getUserId();

        Long id = request.getId();
        GeneratorInstance generatorInstance = generatorInstanceRepository.selectById(id);
        if(generatorInstance == null){
            throw new AppException("实例不存在");
        }

        if(!userId.equals(generatorInstance.getUser().getId())) {
            throw new AppException("权限不足");
        }

        Long generatorId = generatorInstance.getGenerator().getId();
        Generator generator = generatorRepository.selectById(generatorId);
        if(generator == null){
            throw new AppException("生成器不存在");
        }

        if(!generator.getIsOpen() && !generator.getDeveloper().getId().equals(userId)){
            throw new AppException("当前生成器正在维护，请暂停操作等待发布！");
        }

        Long templateStrategyId = request.getTemplateStrategyId();
        TemplateStrategy templateStrategy = templateStrategyRepository.selectById(templateStrategyId);

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
        userClone.setId(userId);
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
            List<String> messages = new ArrayList<>();
            LinkedList<Throwable> exceptionStack = new LinkedList<>();
            exceptionStack.push(e);
            while (!exceptionStack.isEmpty()) {
                Throwable exception = exceptionStack.pop();
                messages.add(exception.toString());
                if(exception.getCause() != null){
                    exceptionStack.push(exception.getCause());
                }
            }
            runResult.setMessages(messages);
            return runResult;
        }
        String generatePath = ConfigProperties.TEMPORARY_PATH + ConfigProperties.fileSeparator + userId + ConfigProperties.fileSeparator + generatorInstance.getName() + "(" + generateId + ")";
        FileUtil.mkdirs(generatePath);
        File generateFolder = new File(generatePath);
        try {
            ZipUtil.compress(generateFolder);
        }
        catch (Exception e){
            throw new AppException(e,"压缩文件失败");
        }
        FileUtil.deleteFile(generateFolder);
        runResult.setUrl(userId + "/" + generatorInstance.getName() + "(" + generateId + ").zip");
        runResult.setFileName(generatorInstance.getName() + "(" + generateId + ").zip");
        return runResult;
    }

    @Override
    public RunResult dataDictionary(GeneratorInstanceDataDictionaryRequest request) {
        RunResult runResult = new RunResult();
        Authentication authentication = request.getAuthentication();
        Long userId = authentication.getUserId();

        Long id = request.getId();
        GeneratorInstance generatorInstance = generatorInstanceRepository.selectById(id);
        if(generatorInstance == null){
            throw new AppException("实例不存在");
        }

        if(!userId.equals(generatorInstance.getUser().getId())) {
            throw new AppException("权限不足");
        }

        Long generatorId = generatorInstance.getGenerator().getId();
        Generator generator = generatorRepository.selectById(generatorId);
        if(generator == null){
            throw new AppException("生成器不存在");
        }

        if(!generator.getIsOpen() && !generator.getDeveloper().getId().equals(userId)){
            throw new AppException("当前生成器正在维护，请暂停操作等待发布！");
        }

        DynamicModelQueryRequest dynamicModelQueryRequest = new DynamicModelQueryRequest();
        dynamicModelQueryRequest.setGeneratorId(generatorId);
        dynamicModelQueryRequest.setAuthentication(authentication);

        List<DynamicModel> dynamicModels = dynamicModelService.query(dynamicModelQueryRequest);
        Map<Long, Map<String, Object>> dynamicModelGroupData = new HashMap<>();
        Map<Long, DynamicModel> dynamicModelCache = new HashMap<>();
        Map<Long, Map<String,  Map<String, Set<String>>>> dynamicModelKeysCache = new HashMap<>();
        dynamicModels.forEach(dynamicModel -> {
            Map<String, Object> groupData = new HashMap<>();
            List<Map<String, Object>> fromGroups = new ArrayList<>();
            groupData.put("fromGroups", fromGroups);
            List<Map<String, Object>> headGroups = new ArrayList<>();
            groupData.put("headGroups", headGroups);
            List<DynamicProperty> heads = new ArrayList<>();
            groupData.put("heads", heads);

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


                String group = property.getGroup();
                Map<String, Object> groupMap = new HashMap<>();
                if(!StringUtils.hasText(group)){
                    groupMap.put("property", property);
                    fromGroups.add(groupMap);
                }
                else{
                    Map<String, Object> prevFromGroup = fromGroups.isEmpty() ? null : fromGroups.get(fromGroups.size() - 1);
                    String prevGroup = prevFromGroup == null ? null : (prevFromGroup.get("group") == null ? null : prevFromGroup.get("group").toString());
                    if(prevFromGroup == null || !group.equals(prevGroup)){
                        groupMap.put("group", group);
                        List<DynamicProperty> children = new ArrayList<>();
                        children.add(property);
                        groupMap.put("children", children);
                        fromGroups.add(groupMap);
                    }
                    if(prevFromGroup != null && group.equals(prevGroup)){
                        List<DynamicProperty> children = (List<DynamicProperty>)prevFromGroup.get("children");
                        children.add(property);
                    }
                }
            });

            List<DynamicProperty> association = dynamicModel.getAssociation();
            association.forEach(property -> {
                if ("Date".equals(property.getType())) {
                    associationKeys_dateTypeKeys.add(property.getName());
                } else if ("Model".equals(property.getType())) {
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

                String group = property.getGroup();
                Map<String, Object> groupMap = new HashMap<>();
                if(!StringUtils.hasText(group)){
                    groupMap.put("property", property);
                    headGroups.add(groupMap);
                }
                else{
                    Map<String, Object> prevHeadGroup = headGroups.isEmpty() ? null : headGroups.get(headGroups.size() - 1);
                    String prevGroup = prevHeadGroup == null ? null : (prevHeadGroup.get("group") == null ? null : prevHeadGroup.get("group").toString());
                    if(prevHeadGroup == null || !group.equals(prevGroup)){
                        groupMap.put("group", group);
                        groupMap.put("span", 1);
                        headGroups.add(groupMap);
                    }
                    if(prevHeadGroup != null && group.equals(prevGroup)){
                        Integer span = (Integer)prevHeadGroup.get("span");
                        prevHeadGroup.put("span", span + 1);
                    }
                    heads.add(property);
                }
            });
            dynamicModelCache.put(newDynamicModel.getId(), newDynamicModel);
            dynamicModelGroupData.put(newDynamicModel.getId(), groupData);
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

        Long generateId = idWorker.nextId();
        ComparisonDateTool comparisonDateTool = new ComparisonDateTool();
        Map<String, String> themeIndex = new HashMap<>();
        themeIndex.put("cerulean", "cerulean");
        themeIndex.put("cosmo", "cosmo");
        themeIndex.put("cyborg", "cyborg");
        themeIndex.put("darkly", "darkly");
        themeIndex.put("default", "default");
        themeIndex.put("flatly", "flatly");
        themeIndex.put("journal", "journal");
        themeIndex.put("lumen", "lumen");
        themeIndex.put("paper", "paper");
        themeIndex.put("readable", "readable");
        themeIndex.put("sandstone", "sandstone");
        themeIndex.put("simplex", "simplex");
        themeIndex.put("slate", "slate");
        themeIndex.put("spacelab","spacelab");
        themeIndex.put("superhero", "superhero");
        themeIndex.put("united", "united");
        themeIndex.put("yeti", "yeti");
        String theme = themeIndex.get(request.getTheme());
        theme = theme == null ? "slate" : theme;

        Global global = new Global();
        global.setGenerateId(generateId);
        global.setTheme(theme);

        User userClone = new User();
        userClone.setId(userId);
        userClone.setUserName(authentication.getUserName());
        global.setUser(userClone);

        GeneratorInstance generatorInstanceClone = new GeneratorInstance();
        generatorInstanceClone.setId(generatorInstance.getId());
        generatorInstanceClone.setName(generatorInstance.getName());
        global.setGeneratorInstance(generatorInstanceClone);

        DataModel rootDataModelClone = dataModelTargetCache.get(rootDataModel.getId());
        String generatePath = ConfigProperties.TEMPORARY_PATH + ConfigProperties.fileSeparator + userId + ConfigProperties.fileSeparator + generatorInstance.getName() + "(" + generateId + ")";

        String indexOutFile = generatePath + ConfigProperties.fileSeparator + "index.html";
        Map<String,Object> indexVarMap = new HashMap<>();
        indexVarMap.put("global", global);
        indexVarMap.put("date", comparisonDateTool);
        indexVarMap.put("data", rootDataModelClone);
        FileUtil.mkdirs(new File(indexOutFile).getParent());
        VelocityUtil.mergeWrite(ConfigProperties.ROOT_PATH + ConfigProperties.fileSeparator + "client/template" , "index.html.vm", indexOutFile, indexVarMap);

        FileUtil.mkdirs(generatePath + ConfigProperties.fileSeparator + "library");
        FileUtil.copyFile(
                new File(ConfigProperties.ROOT_PATH + ConfigProperties.fileSeparator + "client/library/jquery/jquery.min.js"),
                new File(generatePath + ConfigProperties.fileSeparator + "library/jquery.min.js")
        );
        FileUtil.copyFile(
                new File(ConfigProperties.ROOT_PATH + ConfigProperties.fileSeparator + "client/library/bootstrap/js/bootstrap.min.js"),
                new File(generatePath + ConfigProperties.fileSeparator + "library/bootstrap.min.js")
        );
        FileUtil.copyFile(
                new File(ConfigProperties.ROOT_PATH + ConfigProperties.fileSeparator + "client/library/angular/angular.min.js"),
                new File(generatePath + ConfigProperties.fileSeparator + "library/angular.min.js")
        );

        FileUtil.copyFile(
                new File(ConfigProperties.ROOT_PATH + ConfigProperties.fileSeparator + "client/library/bootstrap/theme/" + theme + ".min.css"),
                new File(generatePath + ConfigProperties.fileSeparator + "library/" + theme + ".min.css")
        );

        FileUtil.mkdirs(generatePath + ConfigProperties.fileSeparator + "fonts");
        File[] fontFiles = new File(ConfigProperties.ROOT_PATH + ConfigProperties.fileSeparator + "client/library/bootstrap/fonts").listFiles();
        for(File fontFile : fontFiles){
            FileUtil.copyFile(
                    new File(ConfigProperties.ROOT_PATH + ConfigProperties.fileSeparator + "client/library/bootstrap/fonts/" + fontFile.getName()),
                    new File(generatePath + ConfigProperties.fileSeparator + "fonts/" + fontFile.getName())
            );
        }

        LinkedList<DataModel> dataStack = new LinkedList<>();
        dataStack.push(rootDataModelClone);
        FileUtil.mkdirs(generatePath + ConfigProperties.fileSeparator + "data");
        while (!dataStack.isEmpty()) {
            DataModel dataModel = dataStack.pop();
            if(dataModel != rootDataModelClone){
                String dataModelOutFile = generatePath + ConfigProperties.fileSeparator + "data" + ConfigProperties.fileSeparator + dataModel.getId() + ".html";
                Map<String,Object> dataModelVarMap = new HashMap<>();
                dataModelVarMap.put("global", global);
                dataModelVarMap.put("date", comparisonDateTool);
                dataModelVarMap.put("dataModel", dataModel);
                dataModelVarMap.put("dynamicModelGroupData", dynamicModelGroupData);
                VelocityUtil.mergeWrite(ConfigProperties.ROOT_PATH + ConfigProperties.fileSeparator + "client/template", "dataModel.html.vm", dataModelOutFile, dataModelVarMap);
            }
            dataModel.getChildren().forEach(dataStack :: push);
        }

        File generateFolder = new File(generatePath);
        try {
            ZipUtil.compress(generateFolder);
        }
        catch (Exception e){
            throw new AppException(e,"压缩文件失败");
        }
        FileUtil.deleteFile(generateFolder);
        runResult.setUrl(userId + "/" + generatorInstance.getName() + "(" + generateId + ").zip");
        runResult.setFileName(generatorInstance.getName() + "(" + generateId + ").zip");
        return runResult;
    }

    @Override
    public GeneratorInstance versionSync(GeneratorInstanceVersionSyncRequest request) {
        Long id = request.getId();
        GeneratorInstance generatorInstancePersistence = generatorInstanceRepository.selectById(id);
        if(generatorInstancePersistence == null){
            throw new AppException("实例不存在");
        }

        Long userId = request.getAuthentication().getUserId();
        if(!userId.equals(generatorInstancePersistence.getUser().getId())){
            throw new AppException("权限不足");
        }

        Long generatorId = generatorInstancePersistence.getGenerator().getId();
        Generator generatorPersistence = generatorRepository.selectById(generatorId);
        if(generatorPersistence == null){
            throw new AppException("生成器不存在");
        }

        if(generatorInstancePersistence.getVersion() < generatorPersistence.getVersion()){
            generatorInstancePersistence.setVersion(generatorPersistence.getVersion());
            generatorInstancePersistence.setModifyDate(new Date());
            generatorInstanceRepository.update(generatorInstancePersistence);
        }

        return generatorInstancePersistence;
    }

}
