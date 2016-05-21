package com.lite.blackdream.framework.model;

/**
 * Authentication
 *
 * @author LaineyC
 */
public class Authentication {

    private Long userId;

    private String userName;

    private Role role;

    public Authentication(){

    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    
}
