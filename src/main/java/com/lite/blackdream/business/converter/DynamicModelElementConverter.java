package com.lite.blackdream.business.converter;

import com.lite.blackdream.business.domain.DynamicModel;
import com.lite.blackdream.business.domain.DynamicProperty;
import com.lite.blackdream.business.domain.Generator;
import com.lite.blackdream.framework.component.BaseElementConverter;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

        List<Map<String, Object>> predefinedAssociation = entity.getPredefinedAssociation();
        if(!predefinedAssociation.isEmpty()) {
            Element predefinedAssociationElement = element.addElement("predefinedAssociation");
            predefinedAssociation.forEach(property -> {
                Element propertiesElement = predefinedAssociationElement.addElement("property");
                association.forEach(dynamicProperty -> {
                    String propertyName = dynamicProperty.getName();
                    Object propertyValue = property.get(propertyName);
                    String propertyType = dynamicProperty.getType();
                    if (propertyValue != null) {
                        Element propertyElement = propertiesElement.addElement(propertyName);
                        if ("Boolean".equals(propertyType)) {
                            propertyElement.setText(propertyValue.toString());
                        }
                        else if ("Long".equals(propertyType)) {
                            propertyElement.setText(propertyValue.toString());
                        }
                        else if ("Double".equals(propertyType)) {
                            propertyElement.setText(propertyValue.toString());
                        }
                        else if ("String".equals(propertyType)) {
                            propertyElement.setText(propertyValue.toString());
                        }
                        else if ("Date".equals(propertyType)) {
                            propertyElement.setText(propertyValue.toString());
                        }
                        else if ("Enum".equals(propertyType)) {
                            propertyElement.setText(propertyValue.toString());
                        }
                        else if ("Model".equals(propertyType)) {
                            propertyElement.setText(propertyValue.toString());
                        }
                    }
                });
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

        Node predefinedAssociationNode = element.element("predefinedAssociation");
        if(predefinedAssociationNode != null) {
            List<DynamicProperty> dynamicAssociation = entity.getAssociation();
            ((List<Element>) ((Element) predefinedAssociationNode).elements()).forEach(propertyElement -> {
                Map<String, Object> property = new LinkedHashMap<>();
                dynamicAssociation.forEach(dynamicProperty -> {
                    String propertyName = dynamicProperty.getName();
                    Node propertyNode = propertyElement.element(propertyName);
                    String propertyType = dynamicProperty.getType();
                    if (propertyNode != null) {
                        if ("Boolean".equals(propertyType)) {
                            property.put(propertyName, Boolean.valueOf(propertyNode.getText()));
                        } else if ("Long".equals(propertyType)) {
                            property.put(propertyName, Long.valueOf(propertyNode.getText()));
                        } else if ("Double".equals(propertyType)) {
                            property.put(propertyName, Double.valueOf(propertyNode.getText()));
                        } else if ("String".equals(propertyType)) {
                            property.put(propertyName, propertyNode.getText());
                        } else if ("Date".equals(propertyType)) {
                            property.put(propertyName, Long.valueOf(propertyNode.getText()));
                        } else if ("Enum".equals(propertyType)) {
                            property.put(propertyName, propertyNode.getText());
                        } else if ("Model".equals(propertyType)) {
                            property.put(propertyName, Long.valueOf(propertyNode.getText()));
                        }
                    }
                });
                entity.getPredefinedAssociation().add(property);
            });
        }

        return entity;
    }

}
