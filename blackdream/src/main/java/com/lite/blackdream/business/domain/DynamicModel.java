package com.lite.blackdream.business.domain;

import com.lite.blackdream.framework.model.Domain;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author LaineyC
 */
public class DynamicModel extends Domain{

    private Integer sequence;

    private String name;

    private String cascadeScript;

    private String icon;

    private Boolean isRootChild;

    private List<DynamicProperty> properties = new ArrayList<>();

    private List<DynamicProperty> association = new ArrayList<>();

    private List<DynamicModel> children = new ArrayList<>();

    private List<Map<String, Object>> predefinedAssociation = new ArrayList<>();

    private Generator generator;

    private User developer;

    public DynamicModel(){

    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCascadeScript() {
        return cascadeScript;
    }

    public void setCascadeScript(String cascadeScript) {
        this.cascadeScript = cascadeScript;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Boolean getIsRootChild() {
        return isRootChild;
    }

    public void setIsRootChild(Boolean isRootChild) {
        this.isRootChild = isRootChild;
    }

    public List<DynamicProperty> getProperties() {
        return properties;
    }

    public void setProperties(List<DynamicProperty> properties) {
        this.properties = properties;
    }

    public List<DynamicProperty> getAssociation() {
        return association;
    }

    public void setAssociation(List<DynamicProperty> association) {
        this.association = association;
    }

    public List<DynamicModel> getChildren() {
        return children;
    }

    public void setChildren(List<DynamicModel> children) {
        this.children = children;
    }

    public Generator getGenerator() {
        return generator;
    }

    public void setGenerator(Generator generator) {
        this.generator = generator;
    }

    public List<Map<String, Object>> getPredefinedAssociation() {
        return predefinedAssociation;
    }

    public void setPredefinedAssociation(List<Map<String, Object>> predefinedAssociation) {
        this.predefinedAssociation = predefinedAssociation;
    }

    public User getDeveloper() {
        return developer;
    }

    public void setDeveloper(User developer) {
        this.developer = developer;
    }

}
