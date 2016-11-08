package com.lite.blackdream.sdk.parameter;

import com.lite.blackdream.sdk.Response;
import com.lite.blackdream.sdk.domain.User;

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

