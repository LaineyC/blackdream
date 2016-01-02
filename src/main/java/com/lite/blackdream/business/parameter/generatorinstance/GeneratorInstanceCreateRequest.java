package com.lite.blackdream.business.parameter.generatorinstance;

import com.lite.blackdream.framework.model.Request;

/**
 * @author LaineyC
 */
public class GeneratorInstanceCreateRequest extends Request {

    private String name;

    private Long generatorId;

    public GeneratorInstanceCreateRequest(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getGeneratorId() {
        return generatorId;
    }

    public void setGeneratorId(Long generatorId) {
        this.generatorId = generatorId;
    }

}
