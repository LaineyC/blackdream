package com.lite.blackdream.business.domain.tag;

import com.lite.blackdream.business.domain.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Global
 *
 * @author LaineyC
 */
public class Global {

    private Long generateId;

    private String theme;

    private User user;

    private User developer;

    private Generator generator;

    private TemplateStrategy templateStrategy;

    private GeneratorInstance generatorInstance;

    private Map<Long, Template> templateCache = new HashMap<>();

    public Global(){

    }

    public Long getGenerateId() {
        return generateId;
    }

    public void setGenerateId(Long generateId) {
        this.generateId = generateId;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getDeveloper() {
        return developer;
    }

    public void setDeveloper(User developer) {
        this.developer = developer;
    }

    public Generator getGenerator() {
        return generator;
    }

    public void setGenerator(Generator generator) {
        this.generator = generator;
    }

    public TemplateStrategy getTemplateStrategy() {
        return templateStrategy;
    }

    public void setTemplateStrategy(TemplateStrategy templateStrategy) {
        this.templateStrategy = templateStrategy;
    }

    public GeneratorInstance getGeneratorInstance() {
        return generatorInstance;
    }

    public void setGeneratorInstance(GeneratorInstance generatorInstance) {
        this.generatorInstance = generatorInstance;
    }

    public Map<Long, Template> getTemplateCache() {
        return templateCache;
    }

    public void setTemplateCache(Map<Long, Template> templateCache) {
        this.templateCache = templateCache;
    }

}
