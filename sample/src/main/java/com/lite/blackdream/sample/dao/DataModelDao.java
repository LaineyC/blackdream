package com.lite.blackdream.sample.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lite.blackdream.sample.domain.DataModel;
import com.lite.blackdream.sample.domain.Entity;
import com.lite.blackdream.sample.domain.Module;
import com.lite.blackdream.sample.domain.Project;
import com.lite.blackdream.sdk.Client;
import com.lite.blackdream.sdk.parameter.DataModelCreateRequest;
import com.lite.blackdream.sdk.parameter.DataModelCreateResponse;
import com.lite.blackdream.sdk.parameter.UserLoginRequest;
import com.lite.blackdream.sdk.parameter.UserLoginResponse;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LaineyC
 */
@Repository
public class DataModelDao {

    private static Client CLIENT = new Client("http://localhost:8080/api");
    static {
        //使用接口需要先登录
        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setUserName("root");
        userLoginRequest.setPassword("000000");
        CLIENT.execute(userLoginRequest, UserLoginResponse.class, "user.login");
    }

    public DataModel insert(Project project, DataModel root){
        DataModelCreateRequest dataModelCreateRequest = new DataModelCreateRequest();
        dataModelCreateRequest.setRootId(root.getId());
        dataModelCreateRequest.setName(project.getName());
        dataModelCreateRequest.setDynamicModelId(project.getDynamicModel().getId());
        dataModelCreateRequest.setIsExpand(project.getIsExpand());
        dataModelCreateRequest.setParentId(root.getId());
        Project.Properties properties =  project.getProperties();
        dataModelCreateRequest.setProperties(bean2Map(properties));
        DataModelCreateResponse dataModelCreateResponse = CLIENT.execute(dataModelCreateRequest, DataModelCreateResponse.class, "dataModel.create");
        com.lite.blackdream.sdk.domain.DataModel dataModel = dataModelCreateResponse.getBody();
        project.setId(dataModel.getId());
        return project;
    }

    public DataModel insert(Module module, DataModel root){
        DataModelCreateRequest dataModelCreateRequest = new DataModelCreateRequest();
        dataModelCreateRequest.setRootId(root.getId());
        dataModelCreateRequest.setName(module.getName());
        dataModelCreateRequest.setDynamicModelId(module.getDynamicModel().getId());
        dataModelCreateRequest.setIsExpand(module.getIsExpand());
        dataModelCreateRequest.setParentId(module.getParent().getId());
        Module.Properties properties = module.getProperties();
        dataModelCreateRequest.setProperties(bean2Map(properties));
        DataModelCreateResponse dataModelCreateResponse = CLIENT.execute(dataModelCreateRequest, DataModelCreateResponse.class, "dataModel.create");
        com.lite.blackdream.sdk.domain.DataModel dataModel = dataModelCreateResponse.getBody();
        module.setId(dataModel.getId());
        return module;
    }


    public DataModel insert(Entity entity, DataModel root){
        DataModelCreateRequest dataModelCreateRequest = new DataModelCreateRequest();
        dataModelCreateRequest.setRootId(root.getId());
        dataModelCreateRequest.setName(entity.getName());
        dataModelCreateRequest.setDynamicModelId(entity.getDynamicModel().getId());
        dataModelCreateRequest.setIsExpand(entity.getIsExpand());
        dataModelCreateRequest.setParentId(entity.getParent().getId());
        Entity.Properties properties = entity.getProperties();
        dataModelCreateRequest.setProperties(bean2Map(properties));
        List<Map<String, Object>> association = new ArrayList<>();
        for(Entity.Property property : entity.getAssociation()){
            association.add(bean2Map(property));
        }
        dataModelCreateRequest.setAssociation(association);
        DataModelCreateResponse dataModelCreateResponse = CLIENT.execute(dataModelCreateRequest, DataModelCreateResponse.class, "dataModel.create");
        com.lite.blackdream.sdk.domain.DataModel dataModel = dataModelCreateResponse.getBody();
        entity.setId(dataModel.getId());
        return entity;
    }

    private static ObjectMapper MAPPER = new ObjectMapper();
    private Map<String, Object> bean2Map(Object bean){
        Map<String, Object> map;
        try{
            byte[] content = MAPPER.writeValueAsString(bean).getBytes("UTF-8");
            map = MAPPER.readValue(content, Map.class);
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
        return map;
    }

}