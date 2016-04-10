package com.lite.blackdream.business.domain;

import com.lite.blackdream.framework.model.Domain;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author LaineyC
 */
public class DynamicModel extends Domain{

    private String name;

    private String icon;

    private List<DynamicProperty> properties = new ArrayList<>();

    private List<DynamicProperty> association = new ArrayList<>();

    private List<DynamicModel> children = new ArrayList<>();

    private List<Map<String, Object>> predefinedAssociation = new ArrayList<>();

    private Generator generator;

    public DynamicModel(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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
}
