package com.lite.blackdream.business.test;

import com.lite.blackdream.business.domain.DynamicModel;
import com.lite.blackdream.business.domain.DynamicProperty;
import com.lite.blackdream.business.domain.Generator;
import com.lite.blackdream.business.service.DynamicModelService;
import com.lite.blackdream.framework.util.ServiceTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author LaineyC
 */
public class DynamicModelServiceTest extends ServiceTest {

    @Autowired
    private DynamicModelService dynamicModelService;

    @Test
    public void add(){

        DynamicModel dynamicModel = new DynamicModel();

        dynamicModel.setName("moxing");

        Generator generator = new Generator();
        generator.setId(2L);
        dynamicModel.setGenerator(generator);

        for(int i = 0 ; i < 10 ; i++){
            DynamicProperty dynamicProperty = new DynamicProperty();
            dynamicProperty.setName("aname" + i);
            dynamicProperty.setLabel("alabel" + i);
            //dynamicProperty.setDefaultValue();
            //dynamicProperty.setMultipleValue();
            dynamicProperty.setType("Double");
            dynamicProperty.setViewWidth(100.00 + i);
            dynamicModel.getAssociation().add(dynamicProperty);
        }

        for(int i = 0 ; i < 10 ; i++){
            DynamicProperty dynamicProperty = new DynamicProperty();
            dynamicProperty.setName("name" + i);
            dynamicProperty.setLabel("label" + i);
            //dynamicProperty.setDefaultValue();
            //dynamicProperty.setMultipleValue();
            dynamicProperty.setType("Integer");
            dynamicProperty.setViewWidth(100.00 + i);
            dynamicModel.getProperties().add(dynamicProperty);
        }

        DynamicModel c1 = new DynamicModel();
        c1.setId(1L);
        dynamicModel.getChildren().add(c1);

        DynamicModel c2 = new DynamicModel();
        c2.setId(2L);
        dynamicModel.getChildren().add(c2);

        //dynamicModelService.create(dynamicModel);
    }

}
