package com.lite.blackdream.framework.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LaineyC
 */
public class BeanDefinition {

    private String beanName;

    private Class<?> beanType;

    private List<PropertyDefinition> propertyDefinitions = new ArrayList<>();

    public BeanDefinition(){

    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public Class<?> getBeanType() {
        return beanType;
    }

    public void setBeanType(Class<?> beanType) {
        this.beanType = beanType;
    }

    public List<PropertyDefinition> getPropertyDefinitions() {
        return propertyDefinitions;
    }

    public void setPropertyDefinitions(List<PropertyDefinition> propertyDefinitions) {
        this.propertyDefinitions = propertyDefinitions;
    }

}
