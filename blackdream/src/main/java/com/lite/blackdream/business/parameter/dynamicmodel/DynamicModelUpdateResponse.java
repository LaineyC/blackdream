package com.lite.blackdream.business.parameter.dynamicmodel;

import com.lite.blackdream.business.domain.DynamicModel;
import com.lite.blackdream.framework.model.Response;

/**
 * @author LaineyC
 */
public class DynamicModelUpdateResponse extends Response<DynamicModel> {

    public DynamicModelUpdateResponse(){

    }

    public DynamicModelUpdateResponse(DynamicModel body){
        setBody(body);
    }

}
