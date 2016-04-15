package com.lite.blackdream.business.controller;

import com.lite.blackdream.business.domain.Generator;
import com.lite.blackdream.business.parameter.generator.*;
import com.lite.blackdream.business.service.GeneratorService;
import com.lite.blackdream.framework.component.BaseController;
import com.lite.blackdream.framework.model.PagerResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author LaineyC
 */
@Controller
public class GeneratorController extends BaseController {

    @Autowired
    private GeneratorService generatorService;

    @ResponseBody
    @RequestMapping(params="method=generator.create")
    public GeneratorCreateResponse create(GeneratorCreateRequest request) {
        Generator generator = generatorService.create(request);
        return new GeneratorCreateResponse(generator);
    }

    @ResponseBody
    @RequestMapping(params="method=generator.delete")
    public GeneratorDeleteResponse delete(GeneratorDeleteRequest request) {
        Generator generator = generatorService.delete(request);
        return new GeneratorDeleteResponse(generator);
    }

    @ResponseBody
    @RequestMapping(params="method=generator.get")
    public GeneratorGetResponse get(GeneratorGetRequest request) {
        Generator generator = generatorService.get(request);
        return new GeneratorGetResponse(generator);
    }

    @ResponseBody
    @RequestMapping(params="method=generator.search")
    public GeneratorSearchResponse search(GeneratorSearchRequest request) {
        PagerResult<Generator> pagerResult = generatorService.search(request);
        return new GeneratorSearchResponse(pagerResult);
    }

    @ResponseBody
    @RequestMapping(params="method=generator.update")
    public GeneratorUpdateResponse update(GeneratorUpdateRequest request) {
        Generator generator = generatorService.update(request);
        return new GeneratorUpdateResponse(generator);
    }

}
