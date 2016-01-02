package com.lite.blackdream.business.parameter.datamodel;

import com.lite.blackdream.business.domain.DataModel;
import com.lite.blackdream.framework.model.Response;

/**
 * @author LaineyC
 */
public class DataModelTreeResponse extends Response<DataModel> {

    public DataModelTreeResponse(){

    }

    public DataModelTreeResponse(DataModel body){
        setBody(body);
    }

}
