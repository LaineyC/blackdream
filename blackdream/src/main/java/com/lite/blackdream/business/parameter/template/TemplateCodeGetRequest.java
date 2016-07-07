package com.lite.blackdream.business.parameter.template;

import com.lite.blackdream.framework.model.Request;

/**
 * @author LaineyC
 */
public class TemplateCodeGetRequest extends Request {

    private Long id;

    public TemplateCodeGetRequest(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
