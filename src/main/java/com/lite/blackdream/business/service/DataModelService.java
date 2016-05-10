package com.lite.blackdream.business.service;

import com.lite.blackdream.business.domain.DataModel;
import com.lite.blackdream.business.parameter.datamodel.*;
import com.lite.blackdream.framework.component.Service;

/**
 * @author LaineyC
 */
public interface DataModelService extends Service {

    DataModel create(DataModelCreateRequest request);

    DataModel delete(DataModelDeleteRequest request);

    DataModel get(DataModelGetRequest request);

    DataModel update(DataModelUpdateRequest request);

    DataModel tree(DataModelTreeRequest request);

    void sort(DataModelSortRequest request);

}