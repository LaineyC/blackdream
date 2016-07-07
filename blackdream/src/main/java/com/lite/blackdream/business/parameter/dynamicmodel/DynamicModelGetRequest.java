package com.lite.blackdream.business.parameter.dynamicmodel;

import com.lite.blackdream.framework.model.Request;

/**
 * @author LaineyC
 */
public class DynamicModelGetRequest extends Request {

    private Long id;

    public DynamicModelGetRequest(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
