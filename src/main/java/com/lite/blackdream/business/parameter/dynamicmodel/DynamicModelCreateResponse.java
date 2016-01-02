package com.lite.blackdream.business.parameter.dynamicmodel;

import com.lite.blackdream.business.domain.DynamicModel;
import com.lite.blackdream.framework.model.Response;

/**
 * @author LaineyC
 */
public class DynamicModelCreateResponse extends Response<DynamicModel> {

    public DynamicModelCreateResponse(){

    }

    public DynamicModelCreateResponse(DynamicModel body){
        setBody(body);
    }

}
