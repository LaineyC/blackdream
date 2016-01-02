package com.lite.blackdream.business.parameter.template;

import com.lite.blackdream.framework.model.Base64FileItem;
import com.lite.blackdream.framework.model.Request;

/**
 * @author LaineyC
 */
public class TemplateCreateRequest extends Request {

    private String name;

    private Base64FileItem templateFile;

    private Long generatorId;

    public TemplateCreateRequest(){

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

    public Long getGeneratorId() {
        return generatorId;
    }

    public void setGeneratorId(Long generatorId) {
        this.generatorId = generatorId;
    }

}