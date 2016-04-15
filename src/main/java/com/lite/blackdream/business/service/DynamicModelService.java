package com.lite.blackdream.business.service;

import com.lite.blackdream.business.domain.DynamicModel;
import com.lite.blackdream.business.parameter.dynamicmodel.*;
import com.lite.blackdream.framework.component.Service;
import com.lite.blackdream.framework.model.PagerResult;

import java.util.List;

/**
 * @author LaineyC
 */
public interface DynamicModelService extends Service {

    DynamicModel create(DynamicModelCreateRequest request);

    DynamicModel delete(DynamicModelDeleteRequest request);

    DynamicModel get(DynamicModelGetRequest request);

    List<DynamicModel> query(DynamicModelQueryRequest request);

    PagerResult<DynamicModel> search(DynamicModelSearchRequest request);

    DynamicModel update(DynamicModelUpdateRequest request);

}
