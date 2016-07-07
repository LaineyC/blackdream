package com.lite.blackdream.framework.component;

import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author LaineyC
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring-context.xml")
public abstract class ServiceTest extends AbstractJUnit4SpringContextTests{

    @After
    public void after(){
        try{
            Thread.sleep(10000);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}