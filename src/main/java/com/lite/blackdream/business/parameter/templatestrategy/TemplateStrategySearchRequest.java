package com.lite.blackdream.business.parameter.templatestrategy;

import com.lite.blackdream.framework.model.PagerRequest;

/**
 * @author LaineyC
 */
public class TemplateStrategySearchRequest extends PagerRequest {

    private Long generatorId;

    public TemplateStrategySearchRequest(){

    }

    public Long getGeneratorId() {
        return generatorId;
    }

    public void setGeneratorId(Long generatorId) {
        this.generatorId = generatorId;
    }

}
