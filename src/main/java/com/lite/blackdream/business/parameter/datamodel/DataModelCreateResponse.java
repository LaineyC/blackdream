package com.lite.blackdream.business.parameter.datamodel;

import com.lite.blackdream.business.domain.DataModel;
import com.lite.blackdream.framework.model.Response;

/**
 * @author LaineyC
 */
public class DataModelCreateResponse extends Response<DataModel> {

    public DataModelCreateResponse(){

    }

    public DataModelCreateResponse(DataModel body){
        setBody(body);
    }

}
