package com.lite.blackdream.business.parameter.template;

import com.lite.blackdream.framework.model.Response;

/**
 * @author LaineyC
 */
public class TemplateCodeGetResponse extends Response<String> {

    public TemplateCodeGetResponse(){

    }

    public TemplateCodeGetResponse(String body){
        setBody(body);
    }

}
