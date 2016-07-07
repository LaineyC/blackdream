package com.lite.blackdream.business.parameter.generatorinstance;

import com.lite.blackdream.business.domain.GeneratorInstance;
import com.lite.blackdream.framework.model.Response;

/**
 * @author LaineyC
 */
public class GeneratorInstanceCreateResponse extends Response<GeneratorInstance> {

    public GeneratorInstanceCreateResponse(){

    }

    public GeneratorInstanceCreateResponse(GeneratorInstance body){
        setBody(body);
    }

}
