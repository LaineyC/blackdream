package com.lite.blackdream.business.converter;

import com.lite.blackdream.business.domain.tag.Tag;
import com.lite.blackdream.framework.component.ElementConverter;
import com.lite.blackdream.framework.model.BeanDefinition;
import com.lite.blackdream.framework.model.Domain;
import com.lite.blackdream.framework.util.ReflectionUtil;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import java.util.*;

/**
 * @author LaineyC
 */
public abstract class TagElementConverter<E extends Tag> implements ElementConverter<E> {

    protected static Map<String, TagElementConverter> tagElementConverters = new HashMap<>();

    protected Class<E> entityClass = ReflectionUtil.getSuperClassGenericsType(getClass(), 0);

    protected BeanDefinition beanDefinition = ReflectionUtil.getBeanDefinition(entityClass);

    public TagElementConverter(){

    }

    public Element toElement(E entity){
        Element element = DocumentHelper.createElement(beanDefinition.getBeanName());
        beanDefinition.getPropertyDefinitions().forEach(propertyDefinition -> {
            String propertyName = propertyDefinition.getPropertyName();
            Class<?> propertyType = propertyDefinition.getPropertyType();
            Object propertyValue = propertyDefinition.invokeGetMethod(entity);
            if (propertyValue != null && !"tagName".equals(propertyName)) {
                if (String.class.isAssignableFrom(propertyType)) {
                    element.addAttribute(propertyName, (String)propertyValue);
                }
                else if (Domain.class.isAssignableFrom(propertyType)) {
                    element.addAttribute(propertyName, ((Domain)propertyValue).getId().toString());
                }
                else if("children".equals(propertyName)){
                    Collection<Tag> children = (Collection<Tag>)propertyValue;
                    children.forEach(tag -> {
                        TagElementConverter childElementConverter = tagElementConverters.get(tag.getClass().getSimpleName());
                        Element childElement = childElementConverter.toElement(tag);
                        element.add(childElement);
                    });
                }
                else if(!Collection.class.isAssignableFrom(propertyType)){
                    element.addAttribute(propertyName, propertyValue.toString());
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
            throw new RuntimeException();
        }
        beanDefinition.getPropertyDefinitions().forEach(propertyDefinition -> {
            String propertyName = propertyDefinition.getPropertyName();
            Class<?> propertyType = propertyDefinition.getPropertyType();
            if(!"tagName".equals(propertyName)) {
                if (String.class.isAssignableFrom(propertyType)) {
                    String attributeValue = element.attributeValue(propertyName);
                    if (attributeValue != null) {
                        propertyDefinition.invokeSetMethod(entity, attributeValue);
                    }
                }
                else if (Domain.class.isAssignableFrom(propertyType)) {
                    String attributeValue = element.attributeValue(propertyName);
                    if (attributeValue != null) {
                        try {
                            Domain domainField = (Domain)propertyType.newInstance();
                            domainField.setId(Long.valueOf(attributeValue));
                            propertyDefinition.invokeSetMethod(entity, domainField);
                        }
                        catch (Exception e){
                            //
                        }
                    }
                }
                else if ("children".equals(propertyName)) {
                    ((List<Element>) element.elements()).forEach(childElement -> {
                        String childName = childElement.getName();
                        TagElementConverter childElementConverter = tagElementConverters.get(childName);
                        Tag child = childElementConverter.fromElement(childElement);
                        entity.getChildren().add(child);
                    });
                }
                else if (!Collection.class.isAssignableFrom(propertyType)) {
                    String attributeValue = element.attributeValue(propertyName);
                    if (attributeValue != null) {
                        try {
                            Object fieldValue = propertyType.getDeclaredConstructor(String.class).newInstance(attributeValue);
                            propertyDefinition.invokeSetMethod(entity, fieldValue);
                        }
                        catch (Exception e) {
                            //
                        }
                    }
                }
            }
        });
        return entity;
    }

}
