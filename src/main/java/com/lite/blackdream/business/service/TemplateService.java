package com.lite.blackdream.business.service;

import com.lite.blackdream.business.domain.Template;
import com.lite.blackdream.business.parameter.template.*;
import com.lite.blackdream.framework.layer.Service;
import com.lite.blackdream.framework.model.PagerResult;

import java.util.List;

/**
 * @author LaineyC
 */
public interface TemplateService extends Service {

    Template create(TemplateCreateRequest request);

    Template get(TemplateGetRequest request);

    Template delete(TemplateDeleteRequest request);

    List<Template> query(TemplateQueryRequest request);

    PagerResult<Template> search(TemplateSearchRequest request);

    Template update(TemplateUpdateRequest request);

    String codeGet(TemplateCodeGetRequest request);

}
