package com.lite.blackdream.business.domain.tag;

import com.lite.blackdream.framework.el.Context;
import com.lite.blackdream.framework.el.Parser;

/**
 * @author LaineyC
 */
public class Set extends Tag{

    private String var;

    private String value;

    public Set(){

    }

    public Set clone(){
        Set set = (Set)super.clone();
        set.setVar(this.getVar());
        set.setValue(this.getValue());
        return set;
    }

    public void execute(Context context){
        String var = this.getValue();
        Object value = Parser.parseObject(this.getValue(), context);
        context.setVariable(var, value);
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
