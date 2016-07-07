package com.lite.blackdream.business.service;

import com.lite.blackdream.business.domain.GeneratorInstance;
import com.lite.blackdream.business.domain.RunResult;
import com.lite.blackdream.business.parameter.generatorinstance.*;
import com.lite.blackdream.business.parameter.generatorinstance.GeneratorInstanceCreateRequest;
import com.lite.blackdream.framework.component.Service;
import com.lite.blackdream.framework.model.PagerResult;

/**
 * @author LaineyC
 */
public interface GeneratorInstanceService extends Service {

    GeneratorInstance create(GeneratorInstanceCreateRequest request);

    GeneratorInstance delete(GeneratorInstanceDeleteRequest request);

    GeneratorInstance get(GeneratorInstanceGetRequest request);

    PagerResult<GeneratorInstance> search(GeneratorInstanceSearchRequest request);

    GeneratorInstance update(GeneratorInstanceUpdateRequest request);

    RunResult run(GeneratorInstanceRunRequest request);

    RunResult dataDictionary(GeneratorInstanceDataDictionaryRequest request);

    GeneratorInstance versionSync(GeneratorInstanceVersionSyncRequest request);

}
