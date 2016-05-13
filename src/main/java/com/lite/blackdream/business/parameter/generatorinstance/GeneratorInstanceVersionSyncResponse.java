package com.lite.blackdream.business.parameter.generatorinstance;

import com.lite.blackdream.business.domain.GeneratorInstance;
import com.lite.blackdream.framework.model.Response;

/**
 * @author LaineyC
 */
public class GeneratorInstanceVersionSyncResponse extends Response<GeneratorInstance> {

    public GeneratorInstanceVersionSyncResponse(){

    }

    public GeneratorInstanceVersionSyncResponse(GeneratorInstance body){
        setBody(body);
    }

}
