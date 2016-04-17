package com.lite.blackdream.business.domain.tag;

import com.lite.blackdream.framework.el.Context;
import com.lite.blackdream.framework.el.Parser;
import java.util.Arrays;

/**
 * @author LaineyC
 */
public class Call extends Tag{

    private String function;

    private String[] arguments;

    public Call(){

    }

    public Call clone(){
        Call call = (Call)super.clone();
        call.setFunction(this.getFunction());
        String[] arguments = this.getArguments();
        if(arguments != null){
            call.setArguments(Arrays.copyOf(arguments, arguments.length));
        }
        return call;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String[] getArguments() {
        return arguments;
    }

    public void setArguments(String[] arguments) {
        this.arguments = arguments;
    }

    public void execute(Context context){
        String functionName = Parser.parseString(this.getFunction(), context);
        Function function = (Function)context.getVariable(functionName);

        Context exeContext = new Context();
        exeContext.mergeVariable(function.getParentContext());

        String[] arguments = function.getArguments();

        for(int i = 0 ; i < arguments.length ; i++){
            Object argumentValue = Parser.parseObject(getArguments()[i], context);
            exeContext.setVariable(arguments[i], argumentValue);
        }
        for(Tag child : function.getChildren()){
            child.setParent(function);
            try{
                child.execute(exeContext);
            }
            catch(Return.Exception returnException){
                return;
            }
        }
    }

}
