package com.lite.blackdream.business.parameter.datamodel;

import com.lite.blackdream.framework.model.Request;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LaineyC
 */
public class DataModelUpdateRequest extends Request {

    private Long id;

    private Long rootId;

    private String name;

    private Boolean isExpand;

    private Map<String, Object> properties = new LinkedHashMap<>();

    private List<Map<String, Object>> association = new ArrayList<>();

    public DataModelUpdateRequest(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean getIsExpand() {
        return isExpand;
    }

    public void setIsExpand(Boolean isExpand) {
        this.isExpand = isExpand;
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
