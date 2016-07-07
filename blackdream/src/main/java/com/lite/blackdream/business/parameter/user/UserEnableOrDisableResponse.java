package com.lite.blackdream.business.parameter.user;

import com.lite.blackdream.business.domain.User;
import com.lite.blackdream.framework.model.Response;

/**
 * @author LaineyC
 */
public class UserEnableOrDisableResponse extends Response<User> {

    public UserEnableOrDisableResponse(){

    }

    public UserEnableOrDisableResponse(User body){
        setBody(body);
    }

}

