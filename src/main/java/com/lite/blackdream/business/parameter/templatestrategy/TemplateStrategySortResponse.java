package com.lite.blackdream.business.parameter.templatestrategy;

import com.lite.blackdream.business.domain.TemplateStrategy;
import com.lite.blackdream.framework.model.Response;

/**
 * @author LaineyC
 */
public class TemplateStrategySortResponse extends Response<TemplateStrategy> {

    public TemplateStrategySortResponse(){

    }

    public TemplateStrategySortResponse(TemplateStrategy body){
        setBody(body);
    }

}
