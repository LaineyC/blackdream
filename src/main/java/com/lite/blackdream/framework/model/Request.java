package com.lite.blackdream.framework.model;

import com.lite.blackdream.business.domain.User;

/**
 * @author LaineyC
 */
public class Request {

    private User currentUser;

    public Request(){

    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

}

