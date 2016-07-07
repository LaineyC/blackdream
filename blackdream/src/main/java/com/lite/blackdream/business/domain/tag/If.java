package com.lite.blackdream.business.domain.tag;

import com.lite.blackdream.framework.el.Context;
import com.lite.blackdream.framework.el.Parser;

/**
 * @author LaineyC
 */
public class If extends Tag {

    private String test;

    public If(){

    }

    public If clone(){
        If ifTag = (If)super.clone();
        ifTag.setTest(this.getTest());
        return ifTag;
    }

    public void execute(Context context){
        Context exeContext = new Context();
        exeContext.mergeVariable(context);
        boolean test = Parser.parseBoolean(this.getTest(), exeContext);
        if(test) {
            this.getChildren().forEach(child -> {
                child.setParent(this);
                child.execute(exeContext);
            });
        }
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }
}
