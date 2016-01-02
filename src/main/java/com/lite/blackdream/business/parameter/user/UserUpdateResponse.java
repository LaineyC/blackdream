package com.lite.blackdream.business.parameter.user;

import com.lite.blackdream.business.domain.User;
import com.lite.blackdream.framework.model.Response;

/**
 * @author LaineyC
 */
public class UserUpdateResponse extends Response<User> {

    public UserUpdateResponse(){

    }

    public UserUpdateResponse(User body){
        setBody(body);
    }

}

