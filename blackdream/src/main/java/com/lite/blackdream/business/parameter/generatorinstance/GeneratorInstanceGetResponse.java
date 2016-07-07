package com.lite.blackdream.business.parameter.generatorinstance;

import com.lite.blackdream.business.domain.GeneratorInstance;
import com.lite.blackdream.framework.model.Response;

/**
 * @author LaineyC
 */
public class GeneratorInstanceGetResponse extends Response<GeneratorInstance> {

    public GeneratorInstanceGetResponse(){

    }

    public GeneratorInstanceGetResponse(GeneratorInstance body){
        setBody(body);
    }

}
