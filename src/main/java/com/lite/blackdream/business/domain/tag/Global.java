package com.lite.blackdream.business.domain.tag;

import com.lite.blackdream.business.domain.Template;
import com.lite.blackdream.business.domain.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Global
 *
 * @author LaineyC
 */
public class Global {

    private Long generateId;

    private User user;

    private Map<Long, Template> templateCache = new HashMap<>();

    public Global(){

    }

    public Long getGenerateId() {
        return generateId;
    }

    public void setGenerateId(Long generateId) {
        this.generateId = generateId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Map<Long, Template> getTemplateCache() {
        return templateCache;
    }

    public void setTemplateCache(Map<Long, Template> templateCache) {
        this.templateCache = templateCache;
    }

}
