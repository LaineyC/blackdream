package com.lite.blackdream.business.parameter.templatestrategy;

import com.lite.blackdream.business.domain.TemplateStrategy;
import com.lite.blackdream.framework.model.PagerResult;
import com.lite.blackdream.framework.model.Response;

/**
 * @author LaineyC
 */
public class TemplateStrategySearchResponse extends Response<PagerResult<TemplateStrategy>> {

    public TemplateStrategySearchResponse(){

    }

    public TemplateStrategySearchResponse(PagerResult<TemplateStrategy> body){
        setBody(body);
    }

}
