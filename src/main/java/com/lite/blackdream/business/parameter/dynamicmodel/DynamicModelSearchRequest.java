package com.lite.blackdream.business.parameter.dynamicmodel;

import com.lite.blackdream.framework.model.PagerRequest;

/**
 * @author LaineyC
 */
public class DynamicModelSearchRequest extends PagerRequest {

    private Long generatorId;

    private String name;

    public DynamicModelSearchRequest(){

    }

    public Long getGeneratorId() {
        return generatorId;
    }

    public void setGeneratorId(Long generatorId) {
        this.generatorId = generatorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
