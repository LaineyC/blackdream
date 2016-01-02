package com.lite.blackdream.business.parameter.templatestrategy;

import com.lite.blackdream.business.domain.TemplateStrategy;
import com.lite.blackdream.framework.model.Response;

/**
 * @author LaineyC
 */
public class TemplateStrategyCreateResponse extends Response<TemplateStrategy> {


    public TemplateStrategyCreateResponse(){

    }

    public TemplateStrategyCreateResponse(TemplateStrategy body){
        setBody(body);
    }

}
