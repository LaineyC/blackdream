package com.lite.blackdream.business.parameter.generatorinstance;

import com.lite.blackdream.framework.model.PagerRequest;

/**
 * @author LaineyC
 */
public class GeneratorInstanceSearchRequest extends PagerRequest {

    private Long userId;

    private String name;

    public GeneratorInstanceSearchRequest(){

    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
