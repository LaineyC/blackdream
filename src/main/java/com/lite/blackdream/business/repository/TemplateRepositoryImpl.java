package com.lite.blackdream.business.repository;

import com.lite.blackdream.business.domain.Template;
import com.lite.blackdream.framework.component.BaseRepository;
import com.lite.blackdream.framework.util.ConfigProperties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import java.io.File;

/**
 * @author LaineyC
 */
@Repository
public class TemplateRepositoryImpl extends BaseRepository<Template, Long> implements TemplateRepository {

    private static final Log logger = LogFactory.getLog("error");

    /**
     * 删除数据
     */
    @Override
    public void remove(Template entity){
        String filePath = ConfigProperties.DATABASE_PATH + ConfigProperties.fileSeparator + beanDefinition.getBeanName() + ConfigProperties.fileSeparator + entity.getId() + ".xml";
        File file = new File(filePath);
        if(!file.delete()){
            logger.error("remove:" + filePath);
            throw new RuntimeException("remove:" + file);
        }
        File templeFile = new File(ConfigProperties.FILEBASE_PATH + entity.getUrl().replace("/", ConfigProperties.fileSeparator));
        if(!templeFile.delete()){
            logger.error("remove:" + filePath);
            throw new RuntimeException("remove:" + file);
        }
        File parentFile = templeFile.getParentFile();
        if(parentFile.exists()){
            String[] files = parentFile.list();
            if(files == null || files.length == 0){
                parentFile.delete();
            }
        }
    }

}
