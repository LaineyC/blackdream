package com.lite.blackdream.business.parameter.template;

import com.lite.blackdream.business.domain.Template;
import com.lite.blackdream.framework.model.Response;

/**
 * @author LaineyC
 */
public class TemplateSortResponse extends Response<Template> {

    public TemplateSortResponse(){

    }

    public TemplateSortResponse(Template body){
        setBody(body);
    }

}
