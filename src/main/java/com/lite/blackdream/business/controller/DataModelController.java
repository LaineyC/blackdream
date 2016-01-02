package com.lite.blackdream.business.controller;

import com.lite.blackdream.business.domain.DataModel;
import com.lite.blackdream.business.parameter.datamodel.*;
import com.lite.blackdream.business.service.DataModelService;
import com.lite.blackdream.framework.layer.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author LaineyC
 */
@Controller
public class DataModelController extends BaseController {

    @Autowired
    private DataModelService dataModelService;

    @ResponseBody
    @RequestMapping(params="method=dataModel.create")
    public DataModelCreateResponse create(DataModelCreateRequest request) {
        DataModel dataModel = dataModelService.create(request);
        return new DataModelCreateResponse(dataModel);
    }

    @ResponseBody
    @RequestMapping(params="method=dataModel.delete")
    public DataModelDeleteResponse delete(DataModelDeleteRequest request) {
        DataModel dataModel = dataModelService.delete(request);
        return new DataModelDeleteResponse(dataModel);
    }

    @ResponseBody
    @RequestMapping(params="method=dataModel.get")
    public DataModelGetResponse get(DataModelGetRequest request) {
        DataModel dataModel = dataModelService.get(request);
        return new DataModelGetResponse(dataModel);
    }

    @ResponseBody
    @RequestMapping(params="method=dataModel.tree")
    public DataModelTreeResponse tree(DataModelTreeRequest request) {
        DataModel dataModel = dataModelService.tree(request);
        return new DataModelTreeResponse(dataModel);
    }

    @ResponseBody
    @RequestMapping(params="method=dataModel.update")
    public DataModelUpdateResponse update(DataModelUpdateRequest request) {
        DataModel dataModel = dataModelService.update(request);
        return new DataModelUpdateResponse(dataModel);
    }

}
