package com.lite.blackdream.business.parameter.user;

import com.lite.blackdream.business.domain.User;
import com.lite.blackdream.framework.model.Response;

/**
 * @author LaineyC
 */
public class UserLogoutResponse extends Response<User> {

    public UserLogoutResponse(){

    }

    public UserLogoutResponse(User body){
        setBody(body);
    }

}

