package com.lite.blackdream.business.domain.tag;

import com.lite.blackdream.framework.el.Context;
import com.lite.blackdream.framework.el.Parser;

/**
 * @author LaineyC
 */
public class Var extends Tag{

    private String name;

    private String value;

    public Var(){

    }

    public Var clone(){
        Var var = (Var)super.clone();
        var.setName(this.getName());
        var.setValue(this.getValue());
        return var;
    }

    public void execute(Context context){
        String name = this.getName();
        Object value = Parser.parseObject(this.getValue(), context);
        context.setVariable(name, value);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
