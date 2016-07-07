package com.lite.blackdream.business.parameter.user;

import com.lite.blackdream.business.domain.User;
import com.lite.blackdream.framework.model.Response;

/**
 * @author LaineyC
 */
public class UserCreateResponse extends Response<User> {

    public UserCreateResponse(){

    }

    public UserCreateResponse(User body){
        setBody(body);
    }

}

