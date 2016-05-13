package com.lite.blackdream.business.parameter.generator;

import com.lite.blackdream.framework.model.Request;

/**
 * @author LaineyC
 */
public class GeneratorOpenRequest extends Request {

    private Long id;

    public GeneratorOpenRequest(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
