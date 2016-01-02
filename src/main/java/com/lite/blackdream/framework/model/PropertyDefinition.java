package com.lite.blackdream.framework.model;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author LaineyC
 */
public class PropertyDefinition {

    private Method getMethod;

    private Method setMethod;

    private Field property;

    private Class<?> propertyType;

    private String propertyName;

    public PropertyDefinition(){

    }

    public Object invokeGetMethod(Object object){
        try{
            return getMethod.invoke(object);
        }
        catch (Exception e){
            return null;
        }
    }

    public void invokeSetMethod(Object object, Object arg){
        try{
            setMethod.invoke(object, arg);
        }
        catch (Exception e){

        }
    }

    public Method getGetMethod() {
        return getMethod;
    }

    public void setGetMethod(Method getMethod) {
        this.getMethod = getMethod;
    }

    public Method getSetMethod() {
        return setMethod;
    }

    public void setSetMethod(Method setMethod) {
        this.setMethod = setMethod;
    }

    public Field getProperty() {
        return property;
    }

    public void setProperty(Field property) {
        this.property = property;
    }

    public Class<?> getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(Class<?> propertyType) {
        this.propertyType = propertyType;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

}
