package com.lite.blackdream.business.domain;

import com.lite.blackdream.business.domain.tag.Tag;
import com.lite.blackdream.framework.el.Context;

/**
 * @author LaineyC
 */
public class TemplateStrategy extends Tag {

    private String name;

    private Generator generator;

    private User developer;

    public TemplateStrategy(){

    }

    public TemplateStrategy clone(){
        TemplateStrategy templateStrategy = (TemplateStrategy)super.clone();
        templateStrategy.setName(this.getName());
        Generator generator = new Generator();
        generator.setId(this.getGenerator().getId());
        generator.setName(this.getGenerator().getName());
        templateStrategy.setGenerator(generator);
        return templateStrategy;
    }

    public void execute(Context context){
        Context exeContext = new Context();
        exeContext.mergeVariable(context);
        this.getChildren().forEach(child -> {
            child.setParent(this);
            child.execute(exeContext);
        });
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

    public User getDeveloper() {
        return developer;
    }

    public void setDeveloper(User developer) {
        this.developer = developer;
    }

}
