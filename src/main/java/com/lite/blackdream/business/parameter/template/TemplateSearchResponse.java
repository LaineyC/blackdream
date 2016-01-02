package com.lite.blackdream.business.parameter.template;

import com.lite.blackdream.business.domain.Template;
import com.lite.blackdream.framework.model.PagerResult;
import com.lite.blackdream.framework.model.Response;

/**
 * @author LaineyC
 */
public class TemplateSearchResponse extends Response<PagerResult<Template>> {

    public TemplateSearchResponse(){

    }

    public TemplateSearchResponse(PagerResult<Template> body){
        setBody(body);
    }

}
