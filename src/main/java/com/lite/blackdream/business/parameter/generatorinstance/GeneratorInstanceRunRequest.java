package com.lite.blackdream.business.parameter.generatorinstance;

import com.lite.blackdream.framework.model.Request;

/**
 * @author LaineyC
 */
public class GeneratorInstanceRunRequest extends Request {

    private Long id;

    private Long templateStrategyId;

    public GeneratorInstanceRunRequest(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTemplateStrategyId() {
        return templateStrategyId;
    }

    public void setTemplateStrategyId(Long templateStrategyId) {
        this.templateStrategyId = templateStrategyId;
    }
}
