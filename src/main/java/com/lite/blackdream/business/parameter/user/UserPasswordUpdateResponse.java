package com.lite.blackdream.business.parameter.user;

import com.lite.blackdream.business.domain.User;
import com.lite.blackdream.framework.model.Response;

/**
 * @author LaineyC
 */
public class UserPasswordUpdateResponse extends Response<User> {

    public UserPasswordUpdateResponse(){

    }

    public UserPasswordUpdateResponse(User body){
        setBody(body);
    }

}

