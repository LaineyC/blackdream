package com.lite.blackdream.business.parameter.generator;

import com.lite.blackdream.framework.model.Request;

/**
 * @author LaineyC
 */
public class GeneratorGetRequest extends Request {

    private Long id;

    public GeneratorGetRequest(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
