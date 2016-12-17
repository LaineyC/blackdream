package com.lite.blackdream.business.parameter.generatorinstance;

import com.lite.blackdream.business.domain.RunResult;
import com.lite.blackdream.framework.model.Response;

/**
 * @author LaineyC
 */
public class GeneratorInstanceRunResponse extends Response<RunResult> {

    public GeneratorInstanceRunResponse(){

    }

    public GeneratorInstanceRunResponse(RunResult body){
        setBody(body);
    }

}
