package com.lite.blackdream.business.parameter.templatestrategy;

import com.lite.blackdream.framework.model.Request;

/**
 * @author LaineyC
 */
public class TemplateStrategyGetRequest extends Request {

    private Long id;

    public TemplateStrategyGetRequest(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
