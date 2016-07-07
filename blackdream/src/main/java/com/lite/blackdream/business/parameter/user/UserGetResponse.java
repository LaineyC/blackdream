package com.lite.blackdream.business.parameter.user;

import com.lite.blackdream.business.domain.User;
import com.lite.blackdream.framework.model.Response;

/**
 * @author LaineyC
 */
public class UserGetResponse extends Response<User> {

    public UserGetResponse(){

    }

    public UserGetResponse(User body){
        setBody(body);
    }

}

