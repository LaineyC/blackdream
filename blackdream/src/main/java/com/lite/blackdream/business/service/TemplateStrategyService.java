package com.lite.blackdream.business.service;

import com.lite.blackdream.business.domain.TemplateStrategy;
import com.lite.blackdream.business.parameter.templatestrategy.*;
import com.lite.blackdream.framework.component.Service;
import com.lite.blackdream.framework.model.PagerResult;
import java.util.List;

/**
 * @author LaineyC
 */
public interface TemplateStrategyService extends Service {

    TemplateStrategy create(TemplateStrategyCreateRequest request);

    TemplateStrategy delete(TemplateStrategyDeleteRequest request);

    TemplateStrategy get(TemplateStrategyGetRequest request);

    List<TemplateStrategy> query(TemplateStrategyQueryRequest request);

    PagerResult<TemplateStrategy> search(TemplateStrategySearchRequest request);

    TemplateStrategy update(TemplateStrategyUpdateRequest request);

    TemplateStrategy sort(TemplateStrategySortRequest request);

}
