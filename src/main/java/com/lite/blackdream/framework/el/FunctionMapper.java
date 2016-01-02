package com.lite.blackdream.framework.el;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LaineyC
 */
public class FunctionMapper extends javax.el.FunctionMapper {

    private static Map<String, Map<String, Method>> functions = new HashMap<>();

    static {
        Map<String, Class> classMap = new HashMap<>();
        classMap.put("fn", Functions.class);
        classMap.forEach((key, clazz) -> {
            Map<String, Method> methods = new HashMap<>();
            functions.put(key, methods);
            for(Method method : clazz.getMethods()){
                if (Modifier.isStatic(method.getModifiers())) {
                    methods.put(method.getName(), method);
                }
            }
        });
    }

    @Override
    public Method resolveFunction(String prefix, String localName) {
        if(!functions.containsKey(prefix)){
            return null;
        }
        return functions.get(prefix).get(localName);
    }

}
