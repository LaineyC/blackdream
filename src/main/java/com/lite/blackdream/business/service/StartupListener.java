package com.lite.blackdream.business.service;

import com.lite.blackdream.business.repository.*;
import com.lite.blackdream.framework.util.ConfigProperties;
import com.lite.blackdream.framework.util.FileUtil;
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

            String rootPath = System.getProperty("blackdream.root");
            ConfigProperties.ROOT_PATH = rootPath;
            ConfigProperties.CODEBASE_PATH = rootPath + ConfigProperties.fileSeparator + "Codebase";
            File codebasePath = new File(ConfigProperties.CODEBASE_PATH);
            if(!codebasePath.exists()){
                codebasePath.mkdirs();
            }

            //尽量按照依赖的情况决定初始化数据
            dynamicModelRepository.init();
            generatorRepository.init();
            generatorInstanceRepository.init();
            dataModelRepository.init();
            templateRepository.init();
            templateStrategyRepository.init();
            userRepository.init();

            //启动数据 管理员
            userService.create();

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