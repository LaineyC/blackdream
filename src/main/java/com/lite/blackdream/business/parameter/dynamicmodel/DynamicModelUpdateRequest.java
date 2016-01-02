package com.lite.blackdream.business.parameter.dynamicmodel;

import com.lite.blackdream.business.domain.DynamicProperty;
import com.lite.blackdream.framework.model.Request;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LaineyC
 */
public class DynamicModelUpdateRequest extends Request {

    private Long id;

    private String name;

    private String icon;

    private List<DynamicProperty> properties = new ArrayList<>();

    private List<DynamicProperty> association = new ArrayList<>();

    private List<Long> children = new ArrayList<>();

    public DynamicModelUpdateRequest(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Long> getChildren() {
        return children;
    }

    public void setChildren(List<Long> children) {
        this.children = children;
    }

}
