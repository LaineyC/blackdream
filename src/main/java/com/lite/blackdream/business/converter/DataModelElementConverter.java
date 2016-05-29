package com.lite.blackdream.business.converter;

import com.lite.blackdream.business.domain.*;
import com.lite.blackdream.business.repository.DynamicModelRepository;
import com.lite.blackdream.framework.component.BaseElementConverter;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LaineyC
 */
@Component
public class DataModelElementConverter extends BaseElementConverter<DataModel> {

    @Autowired
    private DynamicModelRepository dynamicModelRepository;

    public Element toElement(DataModel entity){
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

        Date modifyDate = entity.getModifyDate();
        if(modifyDate != null){
            Element modifyDateElement = element.addElement("modifyDate");
            modifyDateElement.setText(String.valueOf(modifyDate.getTime()));
        }

        String name = entity.getName();
        if(name != null){
            Element nameElement = element.addElement("name");
            nameElement.setText(name);
        }

        DynamicModel dynamicModel = entity.getDynamicModel();
        if(dynamicModel != null){
            Element dynamicModelElement = element.addElement("dynamicModel");
            dynamicModelElement.setText(dynamicModel.getId().toString());
        }

        GeneratorInstance generatorInstance = entity.getGeneratorInstance();
        if(generatorInstance != null){
            Element generatorInstanceElement = element.addElement("generatorInstance");
            generatorInstanceElement.setText(generatorInstance.getId().toString());
        }

        Generator generator = entity.getGenerator();
        if(generator != null){
            Element generatorElement = element.addElement("generator");
            generatorElement.setText(generator.getId().toString());
        }

        Boolean isExpand = entity.getIsExpand();
        if(isExpand != null){
            Element isExpandElement = element.addElement("isExpand");
            isExpandElement.setText(isExpand.toString());
        }

        Integer sequence = entity.getSequence();
        if(sequence != null){
            Element sequenceElement = element.addElement("sequence");
            sequenceElement.setText(sequence.toString());
        }

        DataModel parent = entity.getParent();
        if(parent != null){
            Element parentElement = element.addElement("parent");
            parentElement.setText(parent.getId().toString());
        }

        User user = entity.getUser();
        if(user != null){
            Element userElement = element.addElement("user");
            userElement.setText(user.getId().toString());
        }
/*
        List<DataModel> children = entity.getChildren();
        if(!children.isEmpty()) {
            Element childrenElement = element.addElement("children");
            String ids = "";
            for(int i = 0 ; i < children.size() ; i++){
                ids = ids + dynamicModel.getId();
                if(i != children.size() - 1){
                    ids += ",";
                }
            }
            childrenElement.setText(ids);
        }
*/
        if(dynamicModel != null){
            DynamicModel dynamicModelPersistence = dynamicModelRepository.selectById(dynamicModel.getId());
            Map<String, Object> properties = entity.getProperties();
            if(!properties.isEmpty()) {
                Element propertiesElement = element.addElement("properties");
                List<DynamicProperty> dynamicProperties = dynamicModelPersistence.getProperties();
                dynamicProperties.forEach(dynamicProperty -> {
                    String propertyName = dynamicProperty.getName();
                    Object propertyValue = properties.get(propertyName);
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
            }

            List<Map<String, Object>> association = entity.getAssociation();
            if(!association.isEmpty()) {
                Element associationElement = element.addElement("association");
                List<DynamicProperty> dynamicAssociation = dynamicModelPersistence.getAssociation();
                association.forEach(property -> {
                    Element propertiesElement = associationElement.addElement("property");
                    dynamicAssociation.forEach(dynamicProperty -> {
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
        }
        return element;
    }

    public DataModel fromElement(Element element){
        DataModel entity = new DataModel();

        Node isDeleteNode = element.element("isDelete");
        if(isDeleteNode != null){
            entity.setIsDelete(Boolean.valueOf(isDeleteNode.getText()));
        }

        Node idNode = element.element("id");
        if(idNode != null){
            entity.setId(Long.valueOf(idNode.getText()));
        }

        Node modifyDateNode = element.element("modifyDate");
        if(modifyDateNode != null){
            entity.setModifyDate(new Date(Long.valueOf(modifyDateNode.getText())));
        }

        Node nameNode = element.element("name");
        if(nameNode != null){
            entity.setName(nameNode.getText());
        }

        Node dynamicModelNode = element.element("dynamicModel");
        if(dynamicModelNode != null){
            DynamicModel dynamicModel = new DynamicModel();
            dynamicModel.setId(Long.valueOf(dynamicModelNode.getText()));
            entity.setDynamicModel(dynamicModel);
        }

        Node generatorInstanceNode = element.element("generatorInstance");
        if(generatorInstanceNode != null){
            GeneratorInstance generatorInstance = new GeneratorInstance();
            generatorInstance.setId(Long.valueOf(generatorInstanceNode.getText()));
            entity.setGeneratorInstance(generatorInstance);
        }

        Node generatorNode = element.element("generator");
        if(generatorNode != null){
            Generator generator = new Generator();
            generator.setId(Long.valueOf(generatorNode.getText()));
            entity.setGenerator(generator);
        }

        Node isExpandNode = element.element("isExpand");
        if(isExpandNode != null){
            entity.setIsExpand(Boolean.valueOf(isExpandNode.getText()));
        }

        Node sequenceNode = element.element("sequence");
        if(sequenceNode != null){
            entity.setSequence(Integer.valueOf(sequenceNode.getText()));
        }

        Node parentNode = element.element("parent");
        if(parentNode != null){
            DataModel parent = new DataModel();
            parent.setId(Long.valueOf(parentNode.getText()));
            entity.setParent(parent);
        }

        Node userNode = element.element("user");
        if(userNode != null){
            User user = new User();
            user.setId(Long.valueOf(userNode.getText()));
            entity.setUser(user);
        }
/*
        Node childrenNode = element.selectSingleNode("./children");
        if(childrenNode != null){
            String nodeValue = childrenNode.getText();
            String[] ids = nodeValue.split(",");
            for(String stringId : ids){
                Long id = Long.valueOf(stringId);
                DataModel child = new DataModel();
                child.setId(id);
                entity.getChildren().add(child);
            }
        }
*/
        if(entity.getDynamicModel() != null){
            DynamicModel dynamicModelPersistence = dynamicModelRepository.selectById(entity.getDynamicModel().getId());
            Element propertiesNode = element.element("properties");
            if(propertiesNode != null){
                List<DynamicProperty> dynamicProperties = dynamicModelPersistence.getProperties();
                dynamicProperties.forEach(dynamicProperty -> {
                    String propertyName = dynamicProperty.getName();
                    Node propertyNode = propertiesNode.element(propertyName);
                    String propertyType = dynamicProperty.getType();
                    if (propertyNode != null) {
                        try{
                            if ("Boolean".equals(propertyType)) {
                                entity.getProperties().put(propertyName, Boolean.valueOf(propertyNode.getText()));
                            }
                            else if ("Long".equals(propertyType)) {
                                entity.getProperties().put(propertyName, Long.valueOf(propertyNode.getText()));
                            }
                            else if ("Double".equals(propertyType)) {
                                entity.getProperties().put(propertyName, Double.valueOf(propertyNode.getText()));
                            }
                            else if ("String".equals(propertyType)) {
                                entity.getProperties().put(propertyName, propertyNode.getText());
                            }
                            else if ("Date".equals(propertyType)) {
                                entity.getProperties().put(propertyName, Long.valueOf(propertyNode.getText()));
                            }
                            else if ("Enum".equals(propertyType)) {
                                entity.getProperties().put(propertyName, propertyNode.getText());
                            }
                            else if ("Model".equals(propertyType)) {
                                entity.getProperties().put(propertyName, Long.valueOf(propertyNode.getText()));
                            }
                        }
                        catch (Exception e){
                            //
                        }
                    }
                });
            }

            Node associationNode = element.element("association");
            if(associationNode != null){
                List<DynamicProperty> dynamicAssociation = dynamicModelPersistence.getAssociation();
                ((List<Element>)((Element)associationNode).elements()).forEach(propertyElement -> {
                    Map<String ,Object> property = new LinkedHashMap<>();
                    dynamicAssociation.forEach(dynamicProperty -> {
                        String propertyName = dynamicProperty.getName();
                        Node propertyNode = propertyElement.element(propertyName);
                        String propertyType = dynamicProperty.getType();
                        if (propertyNode != null) {
                            try{
                                if ("Boolean".equals(propertyType)) {
                                    property.put(propertyName, Boolean.valueOf(propertyNode.getText()));
                                }
                                else if ("Long".equals(propertyType)) {
                                    property.put(propertyName, Long.valueOf(propertyNode.getText()));
                                }
                                else if ("Double".equals(propertyType)) {
                                    property.put(propertyName, Double.valueOf(propertyNode.getText()));
                                }
                                else if ("String".equals(propertyType)) {
                                    property.put(propertyName, propertyNode.getText());
                                }
                                else if ("Date".equals(propertyType)) {
                                    property.put(propertyName, Long.valueOf(propertyNode.getText()));
                                }
                                else if ("Enum".equals(propertyType)) {
                                    property.put(propertyName, propertyNode.getText());
                                }
                                else if ("Model".equals(propertyType)) {
                                    property.put(propertyName, Long.valueOf(propertyNode.getText()));
                                }
                            }
                            catch (Exception e){
                                //
                            }
                        }
                    });
                    entity.getAssociation().add(property);
                });
            }
        }
        return entity;
    }

}
