package com.lite.blackdream.framework.component;

import com.lite.blackdream.framework.util.IdWorker;

/**
 * @author LaineyC
 */
public abstract class BaseService implements Service{

    protected IdWorker idWorker = new IdWorker(1L);

}
