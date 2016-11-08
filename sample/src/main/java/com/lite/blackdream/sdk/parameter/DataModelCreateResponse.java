package com.lite.blackdream.sdk.parameter;

import com.lite.blackdream.sdk.domain.DataModel;
import com.lite.blackdream.sdk.Response;

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
