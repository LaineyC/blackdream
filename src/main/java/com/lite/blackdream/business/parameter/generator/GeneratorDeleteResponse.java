package com.lite.blackdream.business.parameter.generator;

import com.lite.blackdream.business.domain.Generator;
import com.lite.blackdream.framework.model.Response;

/**
 * @author LaineyC
 */
public class GeneratorDeleteResponse extends Response<Generator> {

    public GeneratorDeleteResponse(){

    }

    public GeneratorDeleteResponse(Generator body){
        setBody(body);
    }

}
