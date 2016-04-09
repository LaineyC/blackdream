package com.lite.blackdream.business.parameter.generator;

import com.lite.blackdream.framework.model.PagerRequest;

/**
 * @author LaineyC
 */
public class GeneratorSearchRequest extends PagerRequest {

    private String keyword;

    private String name;

    private Boolean isOpen;

    private Long developerId;

    public GeneratorSearchRequest(){

    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }

    public Long getDeveloperId() {
        return developerId;
    }

    public void setDeveloperId(Long developerId) {
        this.developerId = developerId;
    }

}
