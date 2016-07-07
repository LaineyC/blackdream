package com.lite.blackdream.business.parameter.templatestrategy;

import com.lite.blackdream.framework.model.Request;

/**
 * @author LaineyC
 */
public class TemplateStrategyDeleteRequest extends Request {

    private Long id;

    public TemplateStrategyDeleteRequest(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
