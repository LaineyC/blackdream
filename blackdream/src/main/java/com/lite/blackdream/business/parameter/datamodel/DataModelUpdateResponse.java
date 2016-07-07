package com.lite.blackdream.business.parameter.datamodel;

import com.lite.blackdream.business.domain.DataModel;
import com.lite.blackdream.framework.model.Response;

/**
 * @author LaineyC
 */
public class DataModelUpdateResponse extends Response<DataModel> {

    public DataModelUpdateResponse(){

    }

    public DataModelUpdateResponse(DataModel body){
        setBody(body);
    }

}
