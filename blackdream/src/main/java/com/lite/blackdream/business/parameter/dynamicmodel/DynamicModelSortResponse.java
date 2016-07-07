package com.lite.blackdream.business.parameter.dynamicmodel;

import com.lite.blackdream.business.domain.DynamicModel;
import com.lite.blackdream.framework.model.Response;

/**
 * @author LaineyC
 */
public class DynamicModelSortResponse extends Response<DynamicModel> {

    public DynamicModelSortResponse(){

    }

    public DynamicModelSortResponse(DynamicModel body){
        setBody(body);
    }

}
