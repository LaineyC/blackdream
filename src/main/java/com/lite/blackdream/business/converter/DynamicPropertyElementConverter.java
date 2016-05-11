package com.lite.blackdream.business.converter;

import com.lite.blackdream.business.domain.DynamicProperty;
import com.lite.blackdream.framework.component.BaseElementConverter;
import org.dom4j.Attribute;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

/**
 * @author LaineyC
 */
@Component
public class DynamicPropertyElementConverter extends BaseElementConverter<DynamicProperty> {

    public Element toElement(DynamicProperty entity){
        Element element = DocumentHelper.createElement(beanDefinition.getBeanName());

        String name = entity.getName();
        if(name != null){
            Element nameElement = element.addElement("name");
            nameElement.setText(name);
        }

        String label = entity.getLabel();
        if(label != null){
            Element labelElement = element.addElement("label");
            labelElement.setText(label);
        }

        String type = entity.getType();
        if(type != null){
            Element typeElement = element.addElement("type");
            typeElement.setText(type);
        }

        Object defaultValue = entity.getDefaultValue();
        if(defaultValue != null){
            Element defaultValueElement = element.addElement("defaultValue");
            defaultValueElement.setText(defaultValue.toString());
        }

        Double viewWidth = entity.getViewWidth();
        if(viewWidth != null){
            Element viewWidthElement = element.addElement("viewWidth");
            viewWidthElement.setText(viewWidth.toString());
        }

        String[] optionalValues = entity.getOptionalValues();
        if(optionalValues != null){
            Element optionalValuesElement = element.addElement("optionalValues");
            for(String optionalValue : optionalValues){
                Element optionalValueElement = optionalValuesElement.addElement("value");
                if(optionalValue != null){
                    optionalValueElement.setText(optionalValue);
                }
            }
        }

        String cascadeScript = entity.getCascadeScript();
        if(cascadeScript != null){
            Element cascadeScriptElement = element.addElement("cascadeScript");
            cascadeScriptElement.setText(cascadeScript);
        }

        Map<String, Object> validator = entity.getValidator();
        if(validator != null) {
            Element validatorElement = element.addElement("validator");
            validator.forEach((key, value) -> {
                if (value != null) {
                    validatorElement.addAttribute(key, value.toString());
                }
            });
        }

        return element;
    }

    public DynamicProperty fromElement(Element element){
        DynamicProperty entity = new DynamicProperty();

        Node nameNode = element.element("name");
        if(nameNode != null){
            entity.setName(nameNode.getText());
        }

        Node labelNode = element.element("label");
        if(labelNode != null){
            entity.setLabel(labelNode.getText());
        }

        Node typeNode = element.element("type");
        if(typeNode != null){
            entity.setType(typeNode.getText());
        }

        String type = entity.getType();
        Node defaultValueNode = element.element("defaultValue");
        if(defaultValueNode != null){
            String stringValue = defaultValueNode.getText();
            if("Boolean".equals(type)) {
                entity.setDefaultValue(Boolean.valueOf(stringValue));
            }
            else if("Long".equals(type)){
                entity.setDefaultValue(Long.valueOf(stringValue));
            }
            else if("Double".equals(type)){
                entity.setDefaultValue(Double.valueOf(stringValue));
            }
            else if("String".equals(type)){
                entity.setDefaultValue(stringValue);
            }
            else if("Date".equals(type)){
                //
            }
            else if("Enum".equals(type)){
                entity.setDefaultValue(stringValue);
            }
            else if("Model".equals(type)){
                //
            }
        }

        Node viewWidthNode = element.element("viewWidth");
        if(viewWidthNode != null){
            entity.setViewWidth(Double.valueOf(viewWidthNode.getText()));
        }

        Element optionalValuesNode = element.element("optionalValues");
        if(optionalValuesNode != null) {
            List<Element> optionalValuesElements = (List<Element>) optionalValuesNode.elements();
            String[] optionalValues = new String[optionalValuesElements.size()];
            entity.setOptionalValues(optionalValues);
            for (int i = 0; i < optionalValuesElements.size() ; i++) {
                optionalValues[i] = optionalValuesElements.get(i).getText();
            }
        }

        Node cascadeScriptNode = element.element("cascadeScript");
        if(cascadeScriptNode != null){
            entity.setCascadeScript(cascadeScriptNode.getText());
        }

        Element validatorNode = element.element("validator");
        if(validatorNode != null){
            ((List<Attribute>)validatorNode.attributes()).forEach(attribute -> {
                String attributeName = attribute.getName();
                String attributeValue = attribute.getValue();
                if(attributeValue != null){
                    if("required".equals(attributeName)){
                        entity.getValidator().put(attributeName, Boolean.valueOf(attributeValue));
                    }
                    else if("min".equals(attributeName)){
                        entity.getValidator().put(attributeName, Double.valueOf(attributeValue));
                    }
                    else if("max".equals(attributeName)){
                        entity.getValidator().put(attributeName, Double.valueOf(attributeValue));
                    }
                    else if("minlength".equals(attributeName)){
                        entity.getValidator().put(attributeName, Long.valueOf(attributeValue));
                    }
                    else if("maxlength".equals(attributeName)){
                        entity.getValidator().put(attributeName, Long.valueOf(attributeValue));
                    }
                    else if("pattern".equals(attributeName)){
                        entity.getValidator().put(attributeName,attributeValue);
                    }
                    else if("patternTooltip".equals(attributeName)){
                        entity.getValidator().put(attributeName,attributeValue);
                    }
                }
            });
        }

        return entity;
    }

}
