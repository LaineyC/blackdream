package com.lite.blackdream.business.controller;

import com.lite.blackdream.business.domain.Template;
import com.lite.blackdream.business.parameter.template.*;
import com.lite.blackdream.business.service.TemplateService;
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
public class TemplateController extends BaseController {

    @Autowired
    private TemplateService templateService;

    @ResponseBody
    @Security(open = false, role = Role.DEVELOPER)
    @RequestMapping(params = "method=template.create")
    public TemplateCreateResponse create(TemplateCreateRequest request) {
        Template template = templateService.create(request);
        return new TemplateCreateResponse(template);
    }

    @ResponseBody
    @Security(open = false, role = Role.DEVELOPER)
    @RequestMapping(params = "method=template.delete")
    public TemplateDeleteResponse delete(TemplateDeleteRequest request) {
        Template template = templateService.delete(request);
        return new TemplateDeleteResponse(template);
    }

    @ResponseBody
    @Security(open = false, role = Role.USER)
    @RequestMapping(params = "method=template.get")
    public TemplateGetResponse get(TemplateGetRequest request) {
        Template template = templateService.get(request);
        return new TemplateGetResponse(template);
    }

    @ResponseBody
    @Security(open = false, role = Role.USER)
    @RequestMapping(params = "method=template.query")
    public TemplateQueryResponse query(TemplateQueryRequest request) {
        List<Template> result = templateService.query(request);
        return new TemplateQueryResponse(result);
    }

    @ResponseBody
    @Security(open = false, role = Role.USER)
    @RequestMapping(params = "method=template.search")
    public TemplateSearchResponse search(TemplateSearchRequest request) {
        PagerResult<Template> pagerResult = templateService.search(request);
        return new TemplateSearchResponse(pagerResult);
    }

    @ResponseBody
    @Security(open = false, role = Role.DEVELOPER)
    @RequestMapping(params = "method=template.update")
    public TemplateUpdateResponse update(TemplateUpdateRequest request) {
        Template template = templateService.update(request);
        return new TemplateUpdateResponse(template);
    }

    @ResponseBody
    @Security(open = false, role = Role.USER)
    @RequestMapping(params = "method=template.code.get")
    public TemplateCodeGetResponse codeGet(TemplateCodeGetRequest request) {
        String code = templateService.codeGet(request);
        return new TemplateCodeGetResponse(code);
    }

    @ResponseBody
    @Security(open = false, role = Role.DEVELOPER)
    @RequestMapping(params = "method=template.sort")
    public TemplateSortResponse sort(TemplateSortRequest request) {
        Template template = templateService.sort(request);
        return new TemplateSortResponse(template);
    }

}
