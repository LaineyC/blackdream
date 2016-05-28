package com.lite.blackdream.business.parameter.generatorinstance;

import com.lite.blackdream.framework.model.Request;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LaineyC
 */
public class GeneratorInstanceRunRequest extends Request {

    private Long id;

    private Long templateStrategyId;

    private List<Long> excludeIds = new ArrayList<>();

    public GeneratorInstanceRunRequest(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTemplateStrategyId() {
        return templateStrategyId;
    }

    public void setTemplateStrategyId(Long templateStrategyId) {
        this.templateStrategyId = templateStrategyId;
    }

    public List<Long> getExcludeIds() {
        return excludeIds;
    }

    public void setExcludeIds(List<Long> excludeIds) {
        this.excludeIds = excludeIds;
    }

}
