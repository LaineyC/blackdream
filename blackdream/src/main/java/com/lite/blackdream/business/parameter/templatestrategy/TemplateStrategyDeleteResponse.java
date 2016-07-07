package com.lite.blackdream.business.parameter.templatestrategy;

import com.lite.blackdream.business.domain.TemplateStrategy;
import com.lite.blackdream.framework.model.Response;

/**
 * @author LaineyC
 */
public class TemplateStrategyDeleteResponse extends Response<TemplateStrategy> {

    public TemplateStrategyDeleteResponse(){

    }

    public TemplateStrategyDeleteResponse(TemplateStrategy body){
        setBody(body);
    }

}
