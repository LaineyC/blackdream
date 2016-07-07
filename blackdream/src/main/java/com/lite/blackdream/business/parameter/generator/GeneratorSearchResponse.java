package com.lite.blackdream.business.parameter.generator;

import com.lite.blackdream.business.domain.Generator;
import com.lite.blackdream.framework.model.PagerResult;
import com.lite.blackdream.framework.model.Response;

/**
 * @author LaineyC
 */
public class GeneratorSearchResponse extends Response<PagerResult<Generator>> {

    public GeneratorSearchResponse(){

    }

    public GeneratorSearchResponse(PagerResult<Generator> body){
        setBody(body);
    }

}
