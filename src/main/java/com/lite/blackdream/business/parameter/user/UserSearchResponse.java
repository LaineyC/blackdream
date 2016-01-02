package com.lite.blackdream.business.parameter.user;

import com.lite.blackdream.business.domain.User;
import com.lite.blackdream.framework.model.PagerResult;
import com.lite.blackdream.framework.model.Response;

/**
 * @author LaineyC
 */
public class UserSearchResponse extends Response<PagerResult<User>> {

    public UserSearchResponse(){

    }

    public UserSearchResponse(PagerResult<User> body){
        setBody(body);
    }

}

