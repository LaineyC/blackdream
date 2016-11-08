package com.lite.blackdream.sdk.parameter;

import com.lite.blackdream.sdk.Request;

/**
 * @author LaineyC
 */
public class UserLoginRequest extends Request {

    private String userName;

    private String password;

    public UserLoginRequest(){

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}