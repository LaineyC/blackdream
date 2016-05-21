package com.lite.blackdream.business.controller;

import com.lite.blackdream.business.domain.Generator;
import com.lite.blackdream.business.parameter.generator.*;
import com.lite.blackdream.business.service.GeneratorService;
import com.lite.blackdream.framework.aop.Security;
import com.lite.blackdream.framework.component.BaseController;
import com.lite.blackdream.framework.model.PagerResult;
import com.lite.blackdream.framework.model.Role;
import com.lite.blackdream.framework.util.ConfigProperties;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.io.File;
import java.io.IOException;

/**
 * @author LaineyC
 */
@Controller
public class GeneratorController extends BaseController {

    @Autowired
    private GeneratorService generatorService;

    @ResponseBody
    @Security(open = false, role = Role.DEVELOPER)
    @RequestMapping(params = "method=generator.create")
    public GeneratorCreateResponse create(GeneratorCreateRequest request) {
        Generator generator = generatorService.create(request);
        return new GeneratorCreateResponse(generator);
    }

    @ResponseBody
    @Security(open = false, role = Role.DEVELOPER)
    @RequestMapping(params = "method=generator.delete")
    public GeneratorDeleteResponse delete(GeneratorDeleteRequest request) {
        Generator generator = generatorService.delete(request);
        return new GeneratorDeleteResponse(generator);
    }

    @ResponseBody
    @Security(open = false, role = Role.USER)
    @RequestMapping(params = "method=generator.get")
    public GeneratorGetResponse get(GeneratorGetRequest request) {
        Generator generator = generatorService.get(request);
        return new GeneratorGetResponse(generator);
    }

    @ResponseBody
    @Security(open = false, role = Role.USER)
    @RequestMapping(params = "method=generator.search")
    public GeneratorSearchResponse search(GeneratorSearchRequest request) {
        PagerResult<Generator> pagerResult = generatorService.search(request);
        return new GeneratorSearchResponse(pagerResult);
    }

    @ResponseBody
    @Security(open = false, role = Role.USER)
    @RequestMapping(params = "method=generator.authSearch")
    public GeneratorSearchResponse authSearch(GeneratorSearchRequest request) {
        Long userId = request.getAuthentication().getUserId();
        request.setDeveloperId(userId);
        PagerResult<Generator> pagerResult = generatorService.search(request);
        return new GeneratorSearchResponse(pagerResult);
    }

    @ResponseBody
    @Security(open = false, role = Role.DEVELOPER)
    @RequestMapping(params = "method=generator.update")
    public GeneratorUpdateResponse update(GeneratorUpdateRequest request) {
        Generator generator = generatorService.update(request);
        return new GeneratorUpdateResponse(generator);
    }

    @Security(open = false, role = Role.DEVELOPER)
    @RequestMapping(params = "method=generator.export")
    public ResponseEntity<byte[]> export(GeneratorExportRequest request) throws IOException{
        Long userId = request.getAuthentication().getUserId();
        Generator generator = generatorService.export(request);
        File file = new File(ConfigProperties.TEMPORARY_PATH + ConfigProperties.fileSeparator + userId + ConfigProperties.fileSeparator + generator.getName() + "(" + generator.getId() + ").zip");
        HttpHeaders headers = new HttpHeaders();
        String fileName = java.net.URLEncoder.encode(file.getName(),"UTF-8");
        headers.setContentDispositionFormData("attachment", fileName);
        headers.add("filename", fileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
    }

    @ResponseBody
    @Security(open = false, role = Role.DEVELOPER)
    @RequestMapping(params = "method=generator.import")
    public GeneratorImportResponse _import(GeneratorImportRequest request) {
        Generator generator = generatorService._import(request);
        return new GeneratorImportResponse(generator);
    }

    @ResponseBody
    @Security(open = false, role = Role.DEVELOPER)
    @RequestMapping(params = "method=generator.openOrClose")
    public GeneratorOpenOrCloseResponse openOrClose(GeneratorOpenOrCloseRequest request) {
        Generator generator = generatorService.openOrClose(request);
        return new GeneratorOpenOrCloseResponse(generator);
    }

}
