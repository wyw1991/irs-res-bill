package com.dtzhejiang.irs.res.bill.infra.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;


    public static <T>T getBean(Class<T> requiredType) {
        return applicationContext.getBean(requiredType);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ContextUtil.applicationContext = applicationContext;
    }
}
