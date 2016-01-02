package com.lite.blackdream.business.service;

import com.lite.blackdream.framework.util.FileUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.io.File;

/**
 * @author LaineyC
 */
@Service
public class TaskService {

    @Scheduled(cron = "0 0 4 * * ?")
    public void clearCode() {
        FileUtil.deleteFile(new File(FileUtil.codebasePath), false);
    }

}
