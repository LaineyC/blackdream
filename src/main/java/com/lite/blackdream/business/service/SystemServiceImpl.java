package com.lite.blackdream.business.service;

import com.lite.blackdream.business.domain.*;
import com.lite.blackdream.business.parameter.system.DataStatisticRequest;
import com.lite.blackdream.business.repository.*;
import com.lite.blackdream.framework.component.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LaineyC
 */
@Service
public class SystemServiceImpl extends BaseService implements SystemService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GeneratorRepository generatorRepository;

    @Autowired
    private GeneratorInstanceRepository generatorInstanceRepository;

    @Autowired
    private DynamicModelRepository dynamicModelRepository;

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private TemplateStrategyRepository templateStrategyRepository;

    @Override
    public Map<String, Object> dataStatistic(DataStatisticRequest request) {
        Map<String, Object> result = new HashMap<>();

        result.put("userCount", userRepository.count(new User()));
        result.put("generatorCount", generatorRepository.count(new Generator()));
        result.put("generatorInstanceCount", generatorInstanceRepository.count(new GeneratorInstance()));
        result.put("dynamicModelCount", dynamicModelRepository.count(new DynamicModel()));
        result.put("templateCount", templateRepository.count(new Template()));
        result.put("templateStrategyCount", templateStrategyRepository.count(new TemplateStrategy()));

        return result;
    }

}
