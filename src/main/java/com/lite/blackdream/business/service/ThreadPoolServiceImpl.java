package com.lite.blackdream.business.service;

import org.springframework.stereotype.Service;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author LaineyC
 */
@Service
public class ThreadPoolServiceImpl extends ThreadPoolExecutor {

    public ThreadPoolServiceImpl(){
        super(0, Integer.MAX_VALUE, 60L, TimeUnit.MINUTES, new SynchronousQueue<>());
    }

}
