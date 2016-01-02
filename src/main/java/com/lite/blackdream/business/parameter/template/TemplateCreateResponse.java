package com.lite.blackdream.business.parameter.template;

import com.lite.blackdream.business.domain.Template;
import com.lite.blackdream.framework.model.Response;

/**
 * @author LaineyC
 */
public class TemplateCreateResponse extends Response<Template> {

    public TemplateCreateResponse(){

    }

    public TemplateCreateResponse(Template body){
        setBody(body);
    }

}
