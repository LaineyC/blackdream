package com.lite.blackdream.business.parameter.dynamicmodel;

import com.lite.blackdream.business.domain.DynamicModel;
import com.lite.blackdream.framework.model.PagerResult;
import com.lite.blackdream.framework.model.Response;

/**
 * @author LaineyC
 */
public class DynamicModelSearchResponse extends Response<PagerResult<DynamicModel>> {

    public DynamicModelSearchResponse(){

    }

    public DynamicModelSearchResponse(PagerResult<DynamicModel> body){
        setBody(body);
    }

}
