package com.lite.blackdream.business.service;

import com.lite.blackdream.business.domain.*;
import com.lite.blackdream.business.repository.*;
import com.lite.blackdream.framework.util.ConfigProperties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

    private static final Log logger = LogFactory.getLog(StartupListener.class);

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
            long start = System.currentTimeMillis();
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
            ConfigProperties.ROOT_PATH = new File(System.getProperty("blackdream.root")).getPath();

            //尽量按照依赖的情况决定初始化数据
            userRepository.init();
            //添加启动数据 root管理员
            userService.createRoot();
            generatorRepository.init();
            dynamicModelRepository.init();
            dataModelRepository.init();
            templateRepository.init();
            templateStrategyRepository.init();
            generatorInstanceRepository.init();

            threadPoolService.submit(dataModelRepository);
            threadPoolService.submit(dynamicModelRepository);
            threadPoolService.submit(generatorInstanceRepository);
            threadPoolService.submit(generatorRepository);
            threadPoolService.submit(templateRepository);
            threadPoolService.submit(templateStrategyRepository);
            threadPoolService.submit(userRepository);

            logger.info("用户数据量：" + userRepository.count(new User()));
            logger.info("生成实例数据量：" + generatorInstanceRepository.count(new GeneratorInstance()));
            logger.info("生成数据数据量：" + dataModelRepository.count(new DataModel()));
            logger.info("生成器数据量：" + generatorRepository.count(new Generator()));
            logger.info("数据模型数据量：" + dynamicModelRepository.count(new DynamicModel()));
            logger.info("模板文件数据量：" + templateRepository.count(new Template()));
            logger.info("生成策略数据量：" + templateStrategyRepository.count(new TemplateStrategy()));
            logger.info("加载用时：" + (System.currentTimeMillis() - start) + "ms");
        }
    }

}