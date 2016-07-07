package com.lite.blackdream.business.parameter.datamodel;

import com.lite.blackdream.business.domain.DataModel;
import com.lite.blackdream.framework.model.Response;

/**
 * @author LaineyC
 */
public class DataModelSortResponse extends Response<DataModel> {

    public DataModelSortResponse(){

    }

    public DataModelSortResponse(DataModel body){
        setBody(body);
    }

}
