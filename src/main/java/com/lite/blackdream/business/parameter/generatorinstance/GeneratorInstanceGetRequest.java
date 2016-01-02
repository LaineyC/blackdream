package com.lite.blackdream.business.parameter.generatorinstance;

import com.lite.blackdream.framework.model.Request;

/**
 * @author LaineyC
 */
public class GeneratorInstanceGetRequest extends Request {

    private Long id;

    public GeneratorInstanceGetRequest(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
