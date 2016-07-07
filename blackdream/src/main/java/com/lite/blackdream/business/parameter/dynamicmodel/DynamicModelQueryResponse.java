package com.lite.blackdream.business.parameter.dynamicmodel;

import com.lite.blackdream.business.domain.DynamicModel;
import com.lite.blackdream.framework.model.Response;
import java.util.List;

/**
 * @author LaineyC
 */
public class DynamicModelQueryResponse extends Response<List<DynamicModel>> {

    public DynamicModelQueryResponse(){

    }

    public DynamicModelQueryResponse(List<DynamicModel> body){
        setBody(body);
    }

}
