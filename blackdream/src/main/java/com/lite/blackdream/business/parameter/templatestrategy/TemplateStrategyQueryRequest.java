package com.lite.blackdream.business.parameter.templatestrategy;

import com.lite.blackdream.framework.model.Request;

/**
 * @author LaineyC
 */
public class TemplateStrategyQueryRequest extends Request {

    private Long generatorId;

    public TemplateStrategyQueryRequest(){

    }

    public Long getGeneratorId() {
        return generatorId;
    }

    public void setGeneratorId(Long generatorId) {
        this.generatorId = generatorId;
    }

}
