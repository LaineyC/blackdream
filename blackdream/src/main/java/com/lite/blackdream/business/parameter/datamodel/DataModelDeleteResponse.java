package com.lite.blackdream.business.parameter.datamodel;

import com.lite.blackdream.business.domain.DataModel;
import com.lite.blackdream.framework.model.Response;

/**
 * @author LaineyC
 */
public class DataModelDeleteResponse extends Response<DataModel> {

    public DataModelDeleteResponse(){

    }

    public DataModelDeleteResponse(DataModel body){
        setBody(body);
    }

}
