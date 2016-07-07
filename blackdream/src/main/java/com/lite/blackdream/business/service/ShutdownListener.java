package com.lite.blackdream.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Service;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author LaineyC
 */
@Service
public class ShutdownListener implements ApplicationListener<ContextClosedEvent> {

    @Autowired
    private ThreadPoolExecutor threadPoolService;

    @Override
    public void onApplicationEvent(ContextClosedEvent evt) {
        threadPoolService.shutdownNow();
    }

}