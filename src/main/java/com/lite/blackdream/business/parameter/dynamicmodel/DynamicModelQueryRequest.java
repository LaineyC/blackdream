package com.lite.blackdream.business.parameter.dynamicmodel;

import com.lite.blackdream.framework.model.Request;

/**
 * @author LaineyC
 */
public class DynamicModelQueryRequest extends Request {

    private Long generatorId;

    public DynamicModelQueryRequest(){

    }

    public Long getGeneratorId() {
        return generatorId;
    }

    public void setGeneratorId(Long generatorId) {
        this.generatorId = generatorId;
    }

}
