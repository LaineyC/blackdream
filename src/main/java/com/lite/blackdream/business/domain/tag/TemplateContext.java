package com.lite.blackdream.business.domain.tag;

import com.lite.blackdream.framework.el.Context;
import com.lite.blackdream.framework.el.Parser;
import java.util.AbstractMap;
import java.util.Map;

/**
 * @author LaineyC
 */
public class TemplateContext extends Tag{

    private String var;

    private String value;

    public TemplateContext(){

    }

    public TemplateContext clone(){
        TemplateContext templateContext = (TemplateContext)super.clone();
        templateContext.setVar(this.getVar());
        templateContext.setValue(this.getValue());
        return templateContext;
    }

    @Override
    public void execute(Context context) {

    }

    public Map.Entry getTemplateContext(Context context) {
        Object value = Parser.parseObject(this.getValue(), context);
        return new AbstractMap.SimpleEntry<>(this.getVar(), value);
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
