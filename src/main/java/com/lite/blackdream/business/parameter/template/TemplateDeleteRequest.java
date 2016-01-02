package com.lite.blackdream.business.parameter.template;

import com.lite.blackdream.framework.model.Request;

/**
 * @author LaineyC
 */
public class TemplateDeleteRequest extends Request {

    private Long id;

    public TemplateDeleteRequest(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
