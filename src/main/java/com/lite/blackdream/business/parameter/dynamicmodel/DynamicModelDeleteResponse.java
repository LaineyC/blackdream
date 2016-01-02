package com.lite.blackdream.business.parameter.dynamicmodel;

import com.lite.blackdream.business.domain.DynamicModel;
import com.lite.blackdream.framework.model.Response;

/**
 * @author LaineyC
 */
public class DynamicModelDeleteResponse extends Response<DynamicModel> {

    public DynamicModelDeleteResponse(){

    }

    public DynamicModelDeleteResponse(DynamicModel body){
        setBody(body);
    }

}
