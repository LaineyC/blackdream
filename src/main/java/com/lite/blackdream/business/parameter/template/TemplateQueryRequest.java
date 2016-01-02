package com.lite.blackdream.business.parameter.template;

import com.lite.blackdream.framework.model.Request;

/**
 * @author LaineyC
 */
public class TemplateQueryRequest extends Request {

    private Long generatorId;

    public TemplateQueryRequest(){

    }

    public Long getGeneratorId() {
        return generatorId;
    }

    public void setGeneratorId(Long generatorId) {
        this.generatorId = generatorId;
    }

}
