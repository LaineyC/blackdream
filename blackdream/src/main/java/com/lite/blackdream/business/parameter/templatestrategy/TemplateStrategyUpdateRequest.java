package com.lite.blackdream.business.parameter.templatestrategy;

import com.lite.blackdream.framework.model.Request;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author LaineyC
 */
public class TemplateStrategyUpdateRequest extends Request {

    private Long id;

    private String name;

    private List<Map<String, Object>> children = new ArrayList<>();

    public TemplateStrategyUpdateRequest(){

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

    public List<Map<String, Object>> getChildren() {
        return children;
    }

    public void setChildren(List<Map<String, Object>> children) {
        this.children = children;
    }

}
