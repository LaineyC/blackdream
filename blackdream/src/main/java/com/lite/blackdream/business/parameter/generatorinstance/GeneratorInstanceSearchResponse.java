package com.lite.blackdream.business.parameter.generatorinstance;

import com.lite.blackdream.business.domain.GeneratorInstance;
import com.lite.blackdream.framework.model.PagerResult;
import com.lite.blackdream.framework.model.Response;

/**
 * @author LaineyC
 */
public class GeneratorInstanceSearchResponse extends Response<PagerResult<GeneratorInstance>> {

    public GeneratorInstanceSearchResponse(){

    }

    public GeneratorInstanceSearchResponse(PagerResult<GeneratorInstance> body){
        setBody(body);
    }

}
