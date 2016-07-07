package com.lite.blackdream.business.parameter.user;

import com.lite.blackdream.framework.model.PagerRequest;

/**
 * @author LaineyC
 */
public class UserSearchRequest extends PagerRequest {

    private String userName;

    private Boolean isDisable;

    private Boolean isDeveloper;

    public UserSearchRequest(){

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getIsDisable() {
        return isDisable;
    }

    public void setIsDisable(Boolean isDisable) {
        this.isDisable = isDisable;
    }

    public Boolean getIsDeveloper() {
        return isDeveloper;
    }

    public void setIsDeveloper(Boolean isDeveloper) {
        this.isDeveloper = isDeveloper;
    }

}