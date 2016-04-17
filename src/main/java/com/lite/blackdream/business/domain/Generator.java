package com.lite.blackdream.business.domain;

import com.lite.blackdream.framework.model.Domain;

/**
 * @author LaineyC
 */
public class Generator extends Domain {

    private String name;

    private User developer;

    private Boolean isOpen;

    private String description;

    public Generator(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getDeveloper() {
        return developer;
    }

    public Boolean getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }

    public void setDeveloper(User developer) {
        this.developer = developer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
