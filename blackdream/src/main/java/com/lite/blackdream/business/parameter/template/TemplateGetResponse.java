package com.lite.blackdream.business.parameter.template;

import com.lite.blackdream.business.domain.Template;
import com.lite.blackdream.framework.model.Response;

/**
 * @author LaineyC
 */
public class TemplateGetResponse extends Response<Template> {

    public TemplateGetResponse(){

    }

    public TemplateGetResponse(Template body){
        setBody(body);
    }

}
