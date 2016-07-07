package com.lite.blackdream.business.parameter.generator;

import com.lite.blackdream.business.domain.Generator;
import com.lite.blackdream.framework.model.Response;

/**
 * @author LaineyC
 */
public class GeneratorUpdateResponse extends Response<Generator> {

    public GeneratorUpdateResponse(){

    }

    public GeneratorUpdateResponse(Generator body){
        setBody(body);
    }

}
