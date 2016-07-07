package com.lite.blackdream.business.parameter.user;

import com.lite.blackdream.business.domain.User;
import com.lite.blackdream.framework.model.Response;

/**
 * @author LaineyC
 */
public class UserLoginResponse extends Response<User> {

    public UserLoginResponse(){

    }

    public UserLoginResponse(User body){
        setBody(body);
    }

}

