package com.lite.blackdream.business.converter;

import com.lite.blackdream.business.domain.TemplateStrategy;
import com.lite.blackdream.business.domain.tag.*;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author LaineyC
 */
@Component
public class TemplateStrategyElementConverter extends TagElementConverter<TemplateStrategy>{

    private static class BreakTagElementConverter extends TagElementConverter<Break>{}
    private static class CallTagElementConverter extends TagElementConverter<Call>{

        public Element toElement(Call entity){
            Element element = DocumentHelper.createElement(beanDefinition.getBeanName());

            Boolean isDelete = entity.getIsDelete();
            if(isDelete != null) {
                Element isDeleteElement = element.addElement("isDelete");
                isDeleteElement.setText(isDelete.toString());
            }

            Long id = entity.getId();
            if(id != null){
                element.addAttribute("id", id.toString());
            }

            String function = entity.getFunction();
            if(function != null){
                element.addAttribute("function", function);
            }

            List<Tag> children = entity.getChildren();
            children.forEach(tag -> {
                TagElementConverter childElementConverter = tagElementConverters.get(tag.getClass().getSimpleName());
                Element childElement = childElementConverter.toElement(tag);
                element.add(childElement);
            });

            String[] arguments = entity.getArguments();
            if(arguments != null){
                int i = 1;
                for(String argument : arguments){
                    element.addAttribute("argument" + i, argument);
                    i++;
                }
            }

            return element;
        }

        public Call fromElement(Element element){
            Call entity = new Call();

            Node isDeleteNode = element.element("isDelete");
            if(isDeleteNode != null){
                entity.setIsDelete(Boolean.valueOf(isDeleteNode.getText()));
            }

            String idValue = element.attributeValue("id");
            if(idValue != null){
                entity.setId(Long.valueOf(idValue));
            }

            String function = element.attributeValue("function");
            if(function != null){
                entity.setFunction(function);
            }

            ((List<Element>)element.elements()).forEach(childElement -> {
                String childName = childElement.getName();
                TagElementConverter childElementConverter = tagElementConverters.get(childName);
                Tag child = childElementConverter.fromElement(childElement);
                entity.getChildren().add(child);
            });

            String argument;
            int i = 1;
            List<String> arguments = new ArrayList<>();
            while((argument = element.attributeValue("argument" + i)) != null){
                arguments.add(argument);
                i++;
            }
            entity.setArguments(arguments.toArray(new String[arguments.size()]));

            return entity;
        }

    }
    private static class ContinueTagElementConverter extends TagElementConverter<Continue>{}
    private static class FileTagElementConverter extends TagElementConverter<File>{}
    private static class FolderTagElementConverter extends TagElementConverter<Folder>{}
    private static class ForeachTagElementConverter extends TagElementConverter<Foreach>{}
    private static class FunctionTagElementConverter extends TagElementConverter<Function>{

        public Element toElement(Function entity){
            Element element = DocumentHelper.createElement(beanDefinition.getBeanName());

            Boolean isDelete = entity.getIsDelete();
            if(isDelete != null) {
                Element isDeleteElement = element.addElement("isDelete");
                isDeleteElement.setText(isDelete.toString());
            }

            Long id = entity.getId();
            if(id != null){
                element.addAttribute("id", id.toString());
            }

            String name = entity.getName();
            if(name != null){
                element.addAttribute("name", name);
            }

            List<Tag> children = entity.getChildren();
            children.forEach(tag -> {
                TagElementConverter childElementConverter = tagElementConverters.get(tag.getClass().getSimpleName());
                Element childElement = childElementConverter.toElement(tag);
                element.add(childElement);
            });

            String[] arguments = entity.getArguments();
            if(arguments != null){
                int i = 1;
                for(String argument : arguments){
                    element.addAttribute("argument" + i, argument);
                    i++;
                }
            }

            return element;
        }

        public Function fromElement(Element element){
            Function entity = new Function();

            Node isDeleteNode = element.element("isDelete");
            if(isDeleteNode != null){
                entity.setIsDelete(Boolean.valueOf(isDeleteNode.getText()));
            }

            String idValue = element.attributeValue("id");
            if(idValue != null){
                entity.setId(Long.valueOf(idValue));
            }

            String name = element.attributeValue("name");
            if(name != null){
                entity.setName(name);
            }

            ((List<Element>)element.elements()).forEach(childElement -> {
                String childName = childElement.getName();
                TagElementConverter childElementConverter = tagElementConverters.get(childName);
                Tag child = childElementConverter.fromElement(childElement);
                entity.getChildren().add(child);
            });

            String argument;
            int i = 1;
            List<String> arguments = new ArrayList<>();
            while((argument = element.attributeValue("argument" + i)) != null){
                arguments.add(argument);
                i++;
            }
            entity.setArguments(arguments.toArray(new String[arguments.size()]));

            return entity;
        }

    }
    private static class IfTagElementConverter extends TagElementConverter<If>{}
    private static class ReturnTagElementConverter extends TagElementConverter<Return>{}
    private static class SetTagElementConverter extends TagElementConverter<Set>{}
    private static class TemplateContextTagElementConverter extends TagElementConverter<TemplateContext>{}
    private static class VarTagElementConverter extends TagElementConverter<Var>{}

    static{
        tagElementConverters.put(Break.class.getSimpleName(), new BreakTagElementConverter());
        tagElementConverters.put(Call.class.getSimpleName(), new CallTagElementConverter());
        tagElementConverters.put(Continue.class.getSimpleName(), new ContinueTagElementConverter());
        tagElementConverters.put(File.class.getSimpleName(), new FileTagElementConverter());
        tagElementConverters.put(Folder.class.getSimpleName(), new FolderTagElementConverter());
        tagElementConverters.put(Foreach.class.getSimpleName(), new ForeachTagElementConverter());
        tagElementConverters.put(Function.class.getSimpleName(), new FunctionTagElementConverter());
        tagElementConverters.put(If.class.getSimpleName(), new IfTagElementConverter());
        tagElementConverters.put(Return.class.getSimpleName(), new ReturnTagElementConverter());
        tagElementConverters.put(Set.class.getSimpleName(), new SetTagElementConverter());
        tagElementConverters.put(TemplateContext.class.getSimpleName(), new TemplateContextTagElementConverter());
        tagElementConverters.put(Var.class.getSimpleName(), new VarTagElementConverter());
    }

}
