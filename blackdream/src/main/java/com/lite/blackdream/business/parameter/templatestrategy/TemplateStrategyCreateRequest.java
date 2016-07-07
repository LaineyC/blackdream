package com.lite.blackdream.business.parameter.templatestrategy;

import com.lite.blackdream.framework.model.Request;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author LaineyC
 */
public class TemplateStrategyCreateRequest extends Request {

    private Long generatorId;

    private String name;

    private List<Map<String, Object>> children = new ArrayList<>();

    public TemplateStrategyCreateRequest(){

    }

    public Long getGeneratorId() {
        return generatorId;
    }

    public void setGeneratorId(Long generatorId) {
        this.generatorId = generatorId;
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
