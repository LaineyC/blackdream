package com.lite.blackdream.business.service;

import com.lite.blackdream.business.repository.*;
import com.lite.blackdream.framework.util.ConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import java.io.File;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author LaineyC
 */
@Service
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private DataModelRepository dataModelRepository;

    @Autowired
    private DynamicModelRepository dynamicModelRepository;

    @Autowired
    private GeneratorInstanceRepository generatorInstanceRepository;

    @Autowired
    private GeneratorRepository generatorRepository;

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private TemplateStrategyRepository templateStrategyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ThreadPoolExecutor threadPoolService;

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent evt) {
        if (evt.getApplicationContext().getParent() != null) {
            File databasePath = new File(ConfigProperties.DATABASE_PATH);
            if(!databasePath.exists()){
                databasePath.mkdirs();
            }
            File filebasePath = new File(ConfigProperties.FILEBASE_PATH);
            if(!filebasePath.exists()){
                filebasePath.mkdirs();
            }
            File logbasePath = new File(ConfigProperties.LOGBASE_PATH);
            if(!logbasePath.exists()){
                logbasePath.mkdirs();
            }
            File temporaryPath = new File(ConfigProperties.TEMPORARY_PATH);
            if(!temporaryPath.exists()){
                temporaryPath.mkdirs();
            }
            //项目web根路径
            ConfigProperties.ROOT_PATH = System.getProperty("blackdream.root");
            //尽量按照依赖的情况决定初始化数据
            userRepository.init();
            generatorRepository.init();
            dynamicModelRepository.init();
            dataModelRepository.init();
            templateRepository.init();
            templateStrategyRepository.init();
            generatorInstanceRepository.init();
            //添加启动数据 root管理员
            userService.createRoot();
            threadPoolService.submit(dataModelRepository);
            threadPoolService.submit(dynamicModelRepository);
            threadPoolService.submit(generatorInstanceRepository);
            threadPoolService.submit(generatorRepository);
            threadPoolService.submit(templateRepository);
            threadPoolService.submit(templateStrategyRepository);
            threadPoolService.submit(userRepository);
        }
    }

}