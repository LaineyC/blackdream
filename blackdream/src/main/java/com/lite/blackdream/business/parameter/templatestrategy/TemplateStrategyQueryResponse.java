package com.lite.blackdream.business.parameter.templatestrategy;

import com.lite.blackdream.business.domain.TemplateStrategy;
import com.lite.blackdream.framework.model.Response;
import java.util.List;

/**
 * @author LaineyC
 */
public class TemplateStrategyQueryResponse extends Response<List<TemplateStrategy>> {

    public TemplateStrategyQueryResponse(){

    }

    public TemplateStrategyQueryResponse(List<TemplateStrategy> body){
        setBody(body);
    }

}
