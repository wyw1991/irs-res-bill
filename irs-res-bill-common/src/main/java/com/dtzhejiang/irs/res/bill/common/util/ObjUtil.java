package com.dtzhejiang.irs.res.bill.common.util;

import java.lang.reflect.Field;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
public class ObjUtil {

    /**
     * 驼峰式命名法
     */
    public static String toCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = s.toLowerCase();
        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if ("_".charAt(0)==c) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static Object getValue(Object object, String fieldName) {
        try {
            Field field = object.getClass().getDeclaredField(toCamelCase(fieldName));
            field.setAccessible(true);
            return field.get(object);
        } catch (NoSuchFieldException e) {

        } catch (IllegalAccessException e) {

        }
        return null;
    }
    public static void setValue(Object object, String fieldName,String value) {
        try {
            Field field = object.getClass().getDeclaredField(toCamelCase(fieldName));
            field.setAccessible(true);
            field.set(object,value);
            System.out.println(fieldName+"-------------------------------------"+value);
        } catch (NoSuchFieldException e) {

        } catch (IllegalAccessException e) {

        }
    }

    public ObjUtil() {
        super();
    }

    /**
     * 获取字段表达式结果
     * @param object
     * @param el
     * @return
     */
    public static Object getElResult(Object object,String el,String unit){
        if (object == null) {
            return null;
        }
        //测试SpringEL解析器。
        //设置文字模板，其中#{}表示表达式的起止，#user是表达式字符串，表示引用一个变量。
        //String template = "#{#user<0}";
        String template = el;
        //创建表达式解析器。
        ExpressionParser parser = new SpelExpressionParser();

        //通过evaluationContext.setVariable可以在上下文中设定变量。
        EvaluationContext context = new StandardEvaluationContext();
        object=!"是/否".equals(unit)?Integer.parseInt(object+""):object;
        context.setVariable("obj", object);

        //解析表达式，如果表达式是一个模板表达式，需要为解析传入模板解析器上下文。
        Expression expression = parser.parseExpression(template, new TemplateParserContext());

        //使用Expression.getValue()获取表达式的值，这里传入了Evaluation上下文，第二个参数是类型参数，表示返回值的类型。
        return  expression.getValue(context, Object.class);
    }

    public static void main(String[] args) {
        String a="unify_component_invoke_quantity";
        System.out.println(toCamelCase(a));
    }

}
