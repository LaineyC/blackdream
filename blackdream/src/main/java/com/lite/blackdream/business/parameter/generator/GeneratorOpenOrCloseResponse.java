package com.lite.blackdream.business.parameter.generator;

import com.lite.blackdream.business.domain.Generator;
import com.lite.blackdream.framework.model.Response;

/**
 * @author LaineyC
 */
public class GeneratorOpenOrCloseResponse extends Response<Generator> {

    public GeneratorOpenOrCloseResponse(){

    }

    public GeneratorOpenOrCloseResponse(Generator body){
        setBody(body);
    }

}
