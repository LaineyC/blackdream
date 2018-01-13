package com.lite.blackdream.business.domain;

import com.lite.blackdream.framework.model.Domain;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author LaineyC
 */
public class DynamicProperty extends Domain{

    private Boolean canHide;

    private String group;

    private String name;

    private String label;

    /**
    * Boolean,Long,Double,String,Date,Enum,Model
    */
    private String type;

    private Object defaultValue;

    private Double viewWidth;

    private String[] optionalValues;

    private String initScript;

    private String cascadeScript;

    private Map<String, Object> validator = new LinkedHashMap<>();

    public DynamicProperty(){

    }

    public Boolean getCanHide() {
        return canHide;
    }

    public void setCanHide(Boolean canHide) {
        this.canHide = canHide;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Double getViewWidth() {
        return viewWidth;
    }

    public void setViewWidth(Double viewWidth) {
        this.viewWidth = viewWidth;
    }

    public String[] getOptionalValues() {
        return optionalValues;
    }

    public void setOptionalValues(String[] optionalValues) {
        this.optionalValues = optionalValues;
    }

    public String getInitScript() {
        return initScript;
    }

    public void setInitScript(String initScript) {
        this.initScript = initScript;
    }

    public String getCascadeScript() {
        return cascadeScript;
    }

    public void setCascadeScript(String cascadeScript) {
        this.cascadeScript = cascadeScript;
    }

    public Map<String, Object> getValidator() {
        return validator;
    }

    public void setValidator(Map<String, Object> validator) {
        this.validator = validator;
    }

}
