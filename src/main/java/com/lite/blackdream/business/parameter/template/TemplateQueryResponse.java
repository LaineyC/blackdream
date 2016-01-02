package com.lite.blackdream.business.parameter.template;

import com.lite.blackdream.business.domain.Template;
import com.lite.blackdream.framework.model.Response;
import java.util.List;

/**
 * @author LaineyC
 */
public class TemplateQueryResponse extends Response<List<Template>> {

    public TemplateQueryResponse(){

    }

    public TemplateQueryResponse(List<Template> body){
        setBody(body);
    }

}
