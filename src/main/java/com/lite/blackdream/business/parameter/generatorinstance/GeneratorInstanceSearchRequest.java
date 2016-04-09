package com.lite.blackdream.business.parameter.generatorinstance;

import com.lite.blackdream.framework.model.PagerRequest;

/**
 * @author LaineyC
 */
public class GeneratorInstanceSearchRequest extends PagerRequest {

    private String name;

    public GeneratorInstanceSearchRequest(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
