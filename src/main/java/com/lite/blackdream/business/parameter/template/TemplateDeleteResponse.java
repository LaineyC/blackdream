package com.lite.blackdream.business.parameter.template;

import com.lite.blackdream.business.domain.Template;
import com.lite.blackdream.framework.model.Response;

/**
 * @author LaineyC
 */
public class TemplateDeleteResponse extends Response<Template> {

    public TemplateDeleteResponse(){

    }

    public TemplateDeleteResponse(Template body){
        setBody(body);
    }

}
