package com.lite.blackdream.business.parameter.dynamicmodel;

import com.lite.blackdream.business.domain.DynamicModel;
import com.lite.blackdream.framework.model.Response;

/**
 * @author LaineyC
 */
public class DynamicModelGetResponse extends Response<DynamicModel> {

    public DynamicModelGetResponse(){

    }

    public DynamicModelGetResponse(DynamicModel body){
        setBody(body);
    }

}
