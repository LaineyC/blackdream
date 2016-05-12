package com.lite.blackdream.business.parameter.user;

import com.lite.blackdream.business.domain.User;
import com.lite.blackdream.framework.model.Response;

/**
 * @author LaineyC
 */
public class UserPasswordResetResponse extends Response<User> {

    public UserPasswordResetResponse(){

    }

    public UserPasswordResetResponse(User body){
        setBody(body);
    }

}

