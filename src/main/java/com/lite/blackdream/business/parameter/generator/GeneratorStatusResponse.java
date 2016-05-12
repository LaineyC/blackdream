package com.lite.blackdream.business.parameter.generator;

import com.lite.blackdream.business.domain.Generator;
import com.lite.blackdream.framework.model.Response;

/**
 * @author LaineyC
 */
public class GeneratorStatusResponse extends Response<Generator> {

    public GeneratorStatusResponse(){

    }

    public GeneratorStatusResponse(Generator body){
        setBody(body);
    }

}
