package com.lite.blackdream.business.parameter.templatestrategy;

import com.lite.blackdream.business.domain.TemplateStrategy;
import com.lite.blackdream.framework.model.Response;

/**
 * @author LaineyC
 */
public class TemplateStrategyGetResponse extends Response<TemplateStrategy> {


    public TemplateStrategyGetResponse(){

    }

    public TemplateStrategyGetResponse(TemplateStrategy body){
        setBody(body);
    }

}
