package com.lite.blackdream.framework.el;

import javax.el.ELContext;
import javax.el.ExpressionFactory;

/**
 * @author LaineyC
 */
public class Parser {

    public final static ExpressionFactory EXPRESSION_FACTORY = ExpressionFactory.newInstance();

    public static String parseString(String expression, ELContext context){
        return (String) EXPRESSION_FACTORY.createValueExpression(context, expression, String.class).getValue(context);
    }

    public static boolean parseBoolean(String expression, ELContext context){
        return (boolean) EXPRESSION_FACTORY.createValueExpression(context, expression, boolean.class).getValue(context);
    }

    public static Object parseObject(String expression, ELContext context){
        return EXPRESSION_FACTORY.createValueExpression(context, expression, Object.class).getValue(context);
    }

}
