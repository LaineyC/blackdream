package com.lite.blackdream.framework.el;

import org.apache.el.lang.VariableMapperImpl;
import javax.el.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LaineyC
 */
public class Context extends ELContext {

    private Map<String, Object> context = new HashMap<>();

    private CompositeELResolver resolver = new CompositeELResolver();
    {
        resolver.add(new ResourceBundleELResolver());
        resolver.add(new MapELResolver());
        resolver.add(new ListELResolver());
        resolver.add(new ArrayELResolver());
        resolver.add(new BeanELResolver());
    }

    private VariableMapper varMapper = new VariableMapperImpl();

    private javax.el.FunctionMapper funcMapper = new FunctionMapper();

    public Context(){

    }

    public void setVariable(String var, Object value){
        varMapper.setVariable(var, Parser.EXPRESSION_FACTORY.createValueExpression(value, Object.class));
        context.put(var, value);
    }

    public Object getVariable(String var){
        return context.get(var);
    }

    public boolean containsVariable(String var){
        return context.containsKey(var);
    }

    public Map<String, Object> getAllVariable(){
        return context;
    }

    public void mergeVariable(Context context){
        context.getAllVariable().forEach((k, v) ->{
            if(!containsVariable(k)){
                setVariable(k, v);
            }
        });
    }

    @Override
    public ELResolver getELResolver() {
        return resolver;
    }

    @Override
    public javax.el.FunctionMapper getFunctionMapper() {
        return funcMapper;
    }

    @Override
    public VariableMapper getVariableMapper() {
        return varMapper;
    }

}
