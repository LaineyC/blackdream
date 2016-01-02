package com.lite.blackdream.business.converter;

import com.lite.blackdream.business.domain.DynamicModel;
import com.lite.blackdream.business.domain.DynamicProperty;
import com.lite.blackdream.business.domain.Generator;
import com.lite.blackdream.framework.layer.BaseElementConverter;
import com.lite.blackdream.framework.model.Domain;
import com.lite.blackdream.framework.model.PropertyDefinition;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * @author LaineyC
 */
@Component
public class DynamicModelElementConverter extends BaseElementConverter<DynamicModel> {

    @Autowired
    private DynamicPropertyElementConverter dynamicPropertyElementConverter;

    public Element toElement(DynamicModel entity){
        Element element = DocumentHelper.createElement(beanDefinition.getBeanName());

        Boolean isDelete = entity.getIsDelete();
        if(isDelete != null) {
            Element isDeleteElement = element.addElement("isDelete");
            isDeleteElement.setText(isDelete.toString());
        }

        Long id = entity.getId();
        if(id != null) {
            Element idElement = element.addElement("id");
            idElement.setText(id.toString());
        }

        String name = entity.getName();
        if(name != null){
            Element nameElement = element.addElement("name");
            nameElement.setText(name);
        }

        String icon = entity.getIcon();
        if(icon != null){
            Element iconElement = element.addElement("icon");
            iconElement.setText(icon);
        }

        Generator generator = entity.getGenerator();
        if(generator != null){
            Element generatorElement = element.addElement("generator");
            generatorElement.setText(generator.getId().toString());
        }

        List<DynamicModel> children = entity.getChildren();
        if(!children.isEmpty()) {
            Element childrenElement = element.addElement("children");
            String ids = "";
            for(int i = 0 ; i < children.size() ; i++){
                DynamicModel child = children.get(i);
                ids = ids + child.getId();
                if(i != children.size() - 1){
                    ids += ",";
                }
            }
            childrenElement.setText(ids);
        }

        List<DynamicProperty> properties = entity.getProperties();
        if(!properties.isEmpty()) {
            Element fieldElement = element.addElement("properties");
            properties.forEach(dynamicProperty -> {
                Element dynamicPropertyElement = dynamicPropertyElementConverter.toElement(dynamicProperty);
                fieldElement.add(dynamicPropertyElement);
            });
        }

        List<DynamicProperty> association = entity.getAssociation();
        if(!association.isEmpty()) {
            Element fieldElement = element.addElement("association");
            association.forEach(dynamicProperty -> {
                Element dynamicPropertyElement = dynamicPropertyElementConverter.toElement(dynamicProperty);
                fieldElement.add(dynamicPropertyElement);
            });
        }

        return element;
    }

    public DynamicModel fromElement(Element element){
        DynamicModel entity = new DynamicModel();

        Node isDeleteNode = element.element("isDelete");
        if(isDeleteNode != null){
            entity.setIsDelete(Boolean.valueOf(isDeleteNode.getText()));
        }

        Node idNode = element.element("id");
        if(idNode != null){
            entity.setId(Long.valueOf(idNode.getText()));
        }

        Node nameNode = element.element("name");
        if(nameNode != null){
            entity.setName(nameNode.getText());
        }

        Node iconNode = element.element("icon");
        if(iconNode != null){
            entity.setIcon(iconNode.getText());
        }

        Node generatorNode = element.element("generator");
        if(generatorNode != null){
            Generator generator = new Generator();
            generator.setId(Long.valueOf(generatorNode.getText()));
            entity.setGenerator(generator);
        }

        Node childrenNode = element.element("children");
        if(childrenNode != null){
            String nodeValue = childrenNode.getText();
            String[] ids = nodeValue.split(",");
            for(String stringId : ids){
                Long id = Long.valueOf(stringId);
                DynamicModel child = new DynamicModel();
                child.setId(id);
                entity.getChildren().add(child);
            }
        }

        Node propertiesNode = element.element("properties");
        if(propertiesNode != null){
            ((List<Element>)((Element)propertiesNode).elements()).forEach(propertyElement -> {
                DynamicProperty dynamicProperty = dynamicPropertyElementConverter.fromElement(propertyElement);
                entity.getProperties().add(dynamicProperty);
            });
        }

        Node associationNode = element.element("association");
        if(associationNode != null){
            ((List<Element>)((Element)associationNode).elements()).forEach(propertyElement -> {
                DynamicProperty dynamicProperty = dynamicPropertyElementConverter.fromElement(propertyElement);
                entity.getAssociation().add(dynamicProperty);
            });
        }

        return entity;
    }

}
