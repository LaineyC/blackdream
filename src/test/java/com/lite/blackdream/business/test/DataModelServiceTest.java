package com.lite.blackdream.business.test;

import com.lite.blackdream.business.domain.DataModel;
import com.lite.blackdream.business.domain.DynamicModel;
import com.lite.blackdream.business.domain.DynamicProperty;
import com.lite.blackdream.business.domain.Generator;
import com.lite.blackdream.business.repository.DataModelRepository;
import com.lite.blackdream.business.service.DynamicModelService;
import com.lite.blackdream.framework.util.ServiceTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author LaineyC
 */
public class DataModelServiceTest extends ServiceTest {

    @Autowired
    private DataModelRepository dataModelRepository;

    @Test
    public void add(){

        DataModel dataModel = new DataModel();
        dataModel.setId(1L);

        dataModelRepository.insert(dataModel);

    }

}
