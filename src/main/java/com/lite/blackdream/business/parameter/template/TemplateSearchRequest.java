package com.lite.blackdream.business.parameter.template;

import com.lite.blackdream.framework.model.PagerRequest;

/**
 * @author LaineyC
 */
public class TemplateSearchRequest extends PagerRequest {

    private Long generatorId;

    public TemplateSearchRequest(){

    }

    public Long getGeneratorId() {
        return generatorId;
    }

    public void setGeneratorId(Long generatorId) {
        this.generatorId = generatorId;
    }

}
