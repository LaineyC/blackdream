package com.lite.blackdream.business.domain;

import com.lite.blackdream.framework.model.Domain;

/**
 * @author LaineyC
 */
public class Template extends Domain {

    private String name;

    private String url;

    private Generator generator;

    private User developer;

    private Integer sequence;

    public Template(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Generator getGenerator() {
        return generator;
    }

    public void setGenerator(Generator generator) {
        this.generator = generator;
    }

    public User getDeveloper() {
        return developer;
    }

    public void setDeveloper(User developer) {
        this.developer = developer;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }
}
