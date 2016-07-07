package com.lite.blackdream.business.parameter.system;

import com.lite.blackdream.framework.model.Response;
import java.util.Date;

/**
 * @author LaineyC
 */
public class DateCurrentTimeResponse extends Response<Date> {

    public DateCurrentTimeResponse(){

    }

    public DateCurrentTimeResponse(Date body){
        setBody(body);
    }

}
