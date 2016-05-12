package com.lite.blackdream.business.parameter.user;

import com.lite.blackdream.framework.model.Request;

/**
 * @author LaineyC
 */
public class UserUpdateRequest extends Request {

    private Long id;

    private Boolean isDisable;

    private Boolean isDeveloper;

    public UserUpdateRequest(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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