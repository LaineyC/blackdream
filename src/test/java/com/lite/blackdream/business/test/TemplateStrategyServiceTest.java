package com.lite.blackdream.business.test;

import com.lite.blackdream.business.domain.tag.Foreach;
import com.lite.blackdream.business.domain.tag.Function;
import com.lite.blackdream.business.domain.TemplateStrategy;
import com.lite.blackdream.business.service.TemplateStrategyService;
import com.lite.blackdream.framework.util.ServiceTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author LaineyC
 */
public class TemplateStrategyServiceTest extends ServiceTest {

    @Autowired
    private TemplateStrategyService templateStrategyService;

    @Test
    public void add(){
        TemplateStrategy templateStrategy = new TemplateStrategy();
        templateStrategy.setName("cc");

        Function functionTag = new Function();
        functionTag.setName("func");
        functionTag.setArguments(new String[]{"da", "aa"});

        Foreach foreachTag = new Foreach();
        foreachTag.setItems("ids");
        foreachTag.setItem("id");

        functionTag.getChildren().add(foreachTag);

        templateStrategy.getChildren().add(functionTag);
    }

}
