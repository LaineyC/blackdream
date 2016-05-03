package com.lite.blackdream.business.domain;

import com.lite.blackdream.framework.model.Domain;
import java.util.*;

/**
 * @author LaineyC
 */
public class DataModel extends Domain{

    private String name;

    private DynamicModel dynamicModel;

    private GeneratorInstance generatorInstance;

    private Boolean isExpand;

    private DataModel parent;

    private User user;

    private List<DataModel> children = new ArrayList<>();

    private Map<String, Object> properties = new LinkedHashMap<>();

    private List<Map<String, Object>> association = new ArrayList<>();

    public DataModel(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DynamicModel getDynamicModel() {
        return dynamicModel;
    }

    public void setDynamicModel(DynamicModel dynamicModel) {
        this.dynamicModel = dynamicModel;
    }

    public GeneratorInstance getGeneratorInstance() {
        return generatorInstance;
    }

    public void setGeneratorInstance(GeneratorInstance generatorInstance) {
        this.generatorInstance = generatorInstance;
    }

    public Boolean getIsExpand() {
        return isExpand;
    }

    public void setIsExpand(Boolean isExpand) {
        this.isExpand = isExpand;
    }

    public DataModel getParent() {
        return parent;
    }

    public void setParent(DataModel parent) {
        this.parent = parent;
    }

    public List<DataModel> getChildren() {
        return children;
    }

    public void setChildren(List<DataModel> children) {
        this.children = children;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public List<Map<String, Object>> getAssociation() {
        return association;
    }

    public void setAssociation(List<Map<String, Object>> association) {
        this.association = association;
    }

}
