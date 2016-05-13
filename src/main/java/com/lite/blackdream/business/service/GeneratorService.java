package com.lite.blackdream.business.service;

import com.lite.blackdream.business.domain.Generator;
import com.lite.blackdream.business.parameter.generator.*;
import com.lite.blackdream.framework.component.Service;
import com.lite.blackdream.framework.model.PagerResult;

/**
 * @author LaineyC
 */
public interface GeneratorService extends Service {

    Generator create(GeneratorCreateRequest request);

    Generator delete(GeneratorDeleteRequest request);

    Generator get(GeneratorGetRequest request);

    PagerResult<Generator> search(GeneratorSearchRequest request);

    Generator update(GeneratorUpdateRequest request);

    Generator export(GeneratorExportRequest request);

    Generator _import(GeneratorImportRequest request);

    Generator status(GeneratorOpenRequest request);

}
