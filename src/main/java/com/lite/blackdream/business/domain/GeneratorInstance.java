package com.lite.blackdream.business.domain;

import com.lite.blackdream.framework.model.Domain;

/**
 * @author LaineyC
 */
public class GeneratorInstance extends Domain{

    private String name;

    private Generator generator;

    private User user;

    private DataModel dataModel;

    public GeneratorInstance(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Generator getGenerator() {
        return generator;
    }

    public void setGenerator(Generator generator) {
        this.generator = generator;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DataModel getDataModel() {
        return dataModel;
    }

    public void setDataModel(DataModel dataModel) {
        this.dataModel = dataModel;
    }

}
