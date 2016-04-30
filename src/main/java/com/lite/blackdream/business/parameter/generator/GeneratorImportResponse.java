package com.lite.blackdream.business.parameter.generator;

import com.lite.blackdream.business.domain.Generator;
import com.lite.blackdream.framework.model.Response;

/**
 * @author LaineyC
 */
public class GeneratorImportResponse extends Response<Generator> {

    public GeneratorImportResponse(){

    }

    public GeneratorImportResponse(Generator body){
        setBody(body);
    }

}
