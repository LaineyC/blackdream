package com.lite.blackdream.business.parameter.user;

import com.lite.blackdream.framework.model.Request;

/**
 * @author LaineyC
 */
public class UserPasswordResetRequest extends Request {

    private Long id;

    public UserPasswordResetRequest(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}