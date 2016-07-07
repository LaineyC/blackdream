package com.lite.blackdream.business.parameter.generator;

import com.lite.blackdream.framework.model.Request;

/**
 * @author LaineyC
 */
public class GeneratorOpenOrCloseRequest extends Request {

    private Long id;

    private Boolean isOpen;

    public GeneratorOpenOrCloseRequest(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }

}
