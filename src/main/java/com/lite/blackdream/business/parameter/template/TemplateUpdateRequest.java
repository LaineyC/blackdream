package com.lite.blackdream.business.parameter.template;

import com.lite.blackdream.framework.model.Base64FileItem;
import com.lite.blackdream.framework.model.Request;

/**
 * @author LaineyC
 */
public class TemplateUpdateRequest extends Request {

    private Long id;

    private String name;

    private Base64FileItem templateFile;

    public TemplateUpdateRequest(){
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Base64FileItem getTemplateFile() {
        return templateFile;
    }

    public void setTemplateFile(Base64FileItem templateFile) {
        this.templateFile = templateFile;
    }

}
