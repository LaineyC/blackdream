package com.lite.blackdream.sample.domain;

import java.util.*;

/**
 * @author LaineyC
 */
public class DataModel{

    private Long id;

    private String name;

    private DynamicModel dynamicModel;

    private Boolean isExpand;

    private DataModel parent;

    public DataModel(){

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

    public DynamicModel getDynamicModel() {
        return dynamicModel;
    }

    public void setDynamicModel(DynamicModel dynamicModel) {
        this.dynamicModel = dynamicModel;
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

}
