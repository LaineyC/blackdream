package com.lite.blackdream.business.parameter.templatestrategy;

import com.lite.blackdream.business.domain.TemplateStrategy;
import com.lite.blackdream.framework.model.Response;

/**
 * @author LaineyC
 */
public class TemplateStrategyUpdateResponse extends Response<TemplateStrategy> {

    public TemplateStrategyUpdateResponse(){

    }

    public TemplateStrategyUpdateResponse(TemplateStrategy body){
        setBody(body);
    }

}
