package com.lite.blackdream.business.parameter.generatorinstance;

import com.lite.blackdream.framework.model.Request;

/**
 * @author LaineyC
 */
public class GeneratorInstanceUpdateRequest extends Request {

    private Long id;

    private String name;

    private Long templateStrategyId;

    public GeneratorInstanceUpdateRequest(){

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

    public Long getTemplateStrategyId() {
        return templateStrategyId;
    }

    public void setTemplateStrategyId(Long templateStrategyId) {
        this.templateStrategyId = templateStrategyId;
    }

}
