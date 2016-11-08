package com.lite.blackdream.sdk.parameter;

import com.lite.blackdream.sdk.Request;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LaineyC
 */
public class DataModelCreateRequest extends Request {

    private Long rootId;

    private String name;

    private Long dynamicModelId;

    private Boolean isExpand;

    private Long parentId;

    private Map<String, Object> properties = new HashMap<>();

    private List<Map<String, Object>> association = new ArrayList<>();

    public DataModelCreateRequest(){

    }

    public Long getRootId() {
        return rootId;
    }

    public void setRootId(Long rootId) {
        this.rootId = rootId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDynamicModelId() {
        return dynamicModelId;
    }

    public void setDynamicModelId(Long dynamicModelId) {
        this.dynamicModelId = dynamicModelId;
    }

    public Boolean getIsExpand() {
        return isExpand;
    }

    public void setIsExpand(Boolean isExpand) {
        this.isExpand = isExpand;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
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
