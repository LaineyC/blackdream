package com.lite.blackdream.business.parameter.generator;

import com.lite.blackdream.business.domain.Generator;
import com.lite.blackdream.framework.model.Response;

/**
 * @author LaineyC
 */
public class GeneratorCreateResponse extends Response<Generator> {

    public GeneratorCreateResponse(){

    }

    public GeneratorCreateResponse(Generator body){
        setBody(body);
    }

}
