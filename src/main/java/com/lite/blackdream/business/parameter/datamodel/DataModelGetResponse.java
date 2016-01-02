package com.lite.blackdream.business.parameter.datamodel;

import com.lite.blackdream.business.domain.DataModel;
import com.lite.blackdream.framework.model.Response;

/**
 * @author LaineyC
 */
public class DataModelGetResponse extends Response<DataModel> {

    public DataModelGetResponse(){

    }

    public DataModelGetResponse(DataModel body){
        setBody(body);
    }

}
