package com.lite.blackdream.business.parameter.generatorinstance;

import com.lite.blackdream.business.domain.GeneratorInstance;
import com.lite.blackdream.framework.model.Response;

/**
 * @author LaineyC
 */
public class GeneratorInstanceUpdateResponse extends Response<GeneratorInstance> {

    public GeneratorInstanceUpdateResponse(){

    }

    public GeneratorInstanceUpdateResponse(GeneratorInstance body){
        setBody(body);
    }

}
