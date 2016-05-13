package com.lite.blackdream.business.parameter.generator;

import com.lite.blackdream.business.domain.Generator;
import com.lite.blackdream.framework.model.Response;

/**
 * @author LaineyC
 */
public class GeneratorOpenResponse extends Response<Generator> {

    public GeneratorOpenResponse(){

    }

    public GeneratorOpenResponse(Generator body){
        setBody(body);
    }

}
