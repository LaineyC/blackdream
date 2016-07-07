package com.lite.blackdream.business.parameter.template;

import com.lite.blackdream.business.domain.Template;
import com.lite.blackdream.framework.model.Response;

/**
 * @author LaineyC
 */
public class TemplateUpdateResponse extends Response<Template> {

    public TemplateUpdateResponse(){

    }

    public TemplateUpdateResponse(Template body){
        setBody(body);
    }

}
