package com.lite.blackdream.business.controller;

import com.lite.blackdream.business.domain.TemplateStrategy;
import com.lite.blackdream.business.parameter.templatestrategy.*;
import com.lite.blackdream.business.service.TemplateStrategyService;
import com.lite.blackdream.framework.layer.BaseController;
import com.lite.blackdream.framework.model.PagerResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author LaineyC
 */
@Controller
public class TemplateStrategyController extends BaseController {

    @Autowired
    private TemplateStrategyService templateStrategyService;

    @ResponseBody
    @RequestMapping(params="method=templateStrategy.create")
    public TemplateStrategyCreateResponse create(TemplateStrategyCreateRequest request) {
        TemplateStrategy templateStrategy = templateStrategyService.create(request);
        return new TemplateStrategyCreateResponse(templateStrategy);
    }

    @ResponseBody
    @RequestMapping(params="method=templateStrategy.delete")
    public TemplateStrategyDeleteResponse delete(TemplateStrategyDeleteRequest request) {
        TemplateStrategy templateStrategy = templateStrategyService.delete(request);
        return new TemplateStrategyDeleteResponse(templateStrategy);
    }

    @ResponseBody
    @RequestMapping(params="method=templateStrategy.get")
    public TemplateStrategyGetResponse get(TemplateStrategyGetRequest request) {
        TemplateStrategy templateStrategy = templateStrategyService.get(request);
        return new TemplateStrategyGetResponse(templateStrategy);
    }

    @ResponseBody
    @RequestMapping(params="method=templateStrategy.query")
    public TemplateStrategyQueryResponse query(TemplateStrategyQueryRequest request) {
        List<TemplateStrategy> result = templateStrategyService.query(request);
        return new TemplateStrategyQueryResponse(result);
    }

    @ResponseBody
    @RequestMapping(params="method=templateStrategy.search")
    public TemplateStrategySearchResponse search(TemplateStrategySearchRequest request) {
        PagerResult<TemplateStrategy> pagerResult = templateStrategyService.search(request);
        return new TemplateStrategySearchResponse(pagerResult);
    }

    @ResponseBody
    @RequestMapping(params="method=templateStrategy.update")
    public TemplateStrategyUpdateResponse update(TemplateStrategyUpdateRequest request) {
        TemplateStrategy templateStrategy = templateStrategyService.update(request);
        return new TemplateStrategyUpdateResponse(templateStrategy);
    }

}
