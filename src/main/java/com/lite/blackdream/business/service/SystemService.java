package com.lite.blackdream.business.service;

import com.lite.blackdream.business.parameter.system.DataStatisticRequest;
import com.lite.blackdream.framework.component.Service;
import java.util.Map;

/**
 * @author LaineyC
 */
public interface SystemService extends Service {

    Map<String, Object> dataStatistic(DataStatisticRequest request);

}