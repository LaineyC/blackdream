package com.lite.blackdream.business.parameter.generatorinstance;

import com.lite.blackdream.framework.model.Request;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LaineyC
 */
public class GeneratorInstanceDataDictionaryRequest extends Request {

    private Long id;

    private String theme;

    private List<Long> excludeIds = new ArrayList<>();

    public GeneratorInstanceDataDictionaryRequest(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public List<Long> getExcludeIds() {
        return excludeIds;
    }

    public void setExcludeIds(List<Long> excludeIds) {
        this.excludeIds = excludeIds;
    }

}
