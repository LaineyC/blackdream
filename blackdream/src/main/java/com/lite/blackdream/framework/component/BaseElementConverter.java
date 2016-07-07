package com.lite.blackdream.framework.component;

import com.lite.blackdream.framework.model.BeanDefinition;
import com.lite.blackdream.framework.model.Domain;
import com.lite.blackdream.framework.model.PropertyDefinition;
import com.lite.blackdream.framework.util.ReflectionUtil;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import java.util.Collection;
import java.util.Date;

/**
 * @author LaineyC
 */
public abstract class BaseElementConverter<E extends Domain> implements ElementConverter<E> {

    protected Class<E> entityClass = ReflectionUtil.getSuperClassGenericsType(getClass(), 0);

    protected BeanDefinition beanDefinition = ReflectionUtil.getBeanDefinition(entityClass);

    public Element toElement(E entity){
        Element element = DocumentHelper.createElement(beanDefinition.getBeanName());
        beanDefinition.getPropertyDefinitions().forEach(propertyDefinition -> {
            String propertyName = propertyDefinition.getPropertyName();
            Object propertyValue = propertyDefinition.invokeGetMethod(entity);
            Class<?> propertyType = propertyDefinition.getPropertyType();
            if (propertyValue != null) {
                Element fieldElement = element.addElement(propertyName);
                if (Domain.class.isAssignableFrom(propertyType)) {
                    fieldElement.setText(((Domain) propertyValue).getId().toString());
                }
                else if (Date.class.isAssignableFrom(propertyType)) {
                    fieldElement.setText(Long.toString(((Date) propertyValue).getTime()));
                }
                else if(!Collection.class.isAssignableFrom(propertyType)){
                    fieldElement.setText(propertyValue.toString());
                }
            }
        });
        return element;
    }

    public E fromElement(Element element){
        E entity;
        try {
            entity = entityClass.newInstance();
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
        for (PropertyDefinition propertyDefinition : beanDefinition.getPropertyDefinitions()) {
            Node fieldNode = element.element(propertyDefinition.getPropertyName());
            if(fieldNode != null){
                Class<?> propertyType = propertyDefinition.getPropertyType();
                if(Domain.class.isAssignableFrom(propertyType)){
                    try {
                        Domain domainField = (Domain)propertyDefinition.getPropertyType().newInstance();
                        domainField.setId(Long.valueOf(fieldNode.getText()));
                        propertyDefinition.invokeSetMethod(entity, domainField);
                    }
                    catch (Exception e){
                        //
                    }
                }
                else if(Date.class.isAssignableFrom(propertyType)){
                    Date date = new Date(Long.valueOf(fieldNode.getText()));
                    propertyDefinition.invokeSetMethod(entity, date);
                }
                else if(!Collection.class.isAssignableFrom(propertyType)){
                    try {
                        Object fieldValue = propertyType.getDeclaredConstructor(String.class).newInstance(fieldNode.getText());
                        propertyDefinition.invokeSetMethod(entity, fieldValue);
                    }
                    catch (Exception e){
                        //
                    }
                }
            }
        }
        return entity;
    }

}
