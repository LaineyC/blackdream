package com.lite.blackdream.business.parameter.generator;

import com.lite.blackdream.business.domain.Generator;
import com.lite.blackdream.framework.model.Response;

/**
 * @author LaineyC
 */
public class GeneratorGetResponse extends Response<Generator> {

    public GeneratorGetResponse(){

    }

    public GeneratorGetResponse(Generator body){
        setBody(body);
    }

}
