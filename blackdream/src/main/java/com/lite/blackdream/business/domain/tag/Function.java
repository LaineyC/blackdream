package com.lite.blackdream.business.domain.tag;

import com.lite.blackdream.framework.el.Context;
import java.util.Arrays;

/**
 * @author LaineyC
 */
public class Function extends Tag{

    private Context parentContext;

    private String name;

    private String[] arguments;

    public Context getParentContext() {
        return parentContext;
    }

    public Function(){
        
    }

    public Function clone(){
        Function function = (Function)super.clone();
        function.setName(this.getName());
        String[] arguments = this.getArguments();
        if(arguments != null){
            function.setArguments(Arrays.copyOf(arguments, arguments.length));
        }
        return function;
    }

    public void execute(Context context){
        parentContext = context;
        String name = this.getName();
        context.setVariable(name, this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getArguments() {
        return arguments;
    }

    public void setArguments(String[] arguments) {
        this.arguments = arguments;
    }

}
