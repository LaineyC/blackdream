package com.lite.blackdream.business.controller;

import com.lite.blackdream.business.domain.DynamicModel;
import com.lite.blackdream.business.parameter.dynamicmodel.*;
import com.lite.blackdream.business.service.DynamicModelService;
import com.lite.blackdream.framework.aop.Security;
import com.lite.blackdream.framework.component.BaseController;
import com.lite.blackdream.framework.model.PagerResult;
import com.lite.blackdream.framework.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

/**
 * @author LaineyC
 */
@Controller
public class DynamicModelController extends BaseController {

    @Autowired
    private DynamicModelService dynamicModelService;

    @ResponseBody
    @Security(open = false, role = Role.DEVELOPER)
    @RequestMapping(params = "method=dynamicModel.create")
    public DynamicModelCreateResponse create(DynamicModelCreateRequest request) {
        DynamicModel dynamicModel = dynamicModelService.create(request);
        return new DynamicModelCreateResponse(dynamicModel);
    }

    @ResponseBody
    @Security(open = false, role = Role.DEVELOPER)
    @RequestMapping(params = "method=dynamicModel.delete")
    public DynamicModelDeleteResponse delete(DynamicModelDeleteRequest request) {
        DynamicModel dynamicModel = dynamicModelService.delete(request);
        return new DynamicModelDeleteResponse(dynamicModel);
    }

    @ResponseBody
    @Security(open = false, role = Role.USER)
    @RequestMapping(params = "method=dynamicModel.get")
    public DynamicModelGetResponse get(DynamicModelGetRequest request) {
        DynamicModel dynamicModel = dynamicModelService.get(request);
        return new DynamicModelGetResponse(dynamicModel);
    }

    @ResponseBody
    @Security(open = false, role = Role.USER)
    @RequestMapping(params = "method=dynamicModel.query")
    public DynamicModelQueryResponse query(DynamicModelQueryRequest request) {
        List<DynamicModel> result = dynamicModelService.query(request);
        return new DynamicModelQueryResponse(result);
    }

    @ResponseBody
    @Security(open = false, role = Role.USER)
    @RequestMapping(params = "method=dynamicModel.search")
    public DynamicModelSearchResponse search(DynamicModelSearchRequest request) {
        PagerResult<DynamicModel> pagerResult = dynamicModelService.search(request);
        return new DynamicModelSearchResponse(pagerResult);
    }

    @ResponseBody
    @Security(open = false, role = Role.DEVELOPER)
    @RequestMapping(params = "method=dynamicModel.update")
    public DynamicModelUpdateResponse update(DynamicModelUpdateRequest request) {
        DynamicModel dynamicModel = dynamicModelService.update(request);
        return new DynamicModelUpdateResponse(dynamicModel);
    }

    @ResponseBody
    @Security(open = false, role = Role.DEVELOPER)
    @RequestMapping(params = "method=dynamicModel.sort")
    public DynamicModelSortResponse sort(DynamicModelSortRequest request) {
        DynamicModel dynamicModel = dynamicModelService.sort(request);
        return new DynamicModelSortResponse(dynamicModel);
    }

}
