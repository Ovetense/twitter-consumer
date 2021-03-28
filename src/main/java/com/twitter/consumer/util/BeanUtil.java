package com.twitter.consumer.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * The type Bean util.
 *
 * This class is to get a particular class from a static context
 */
@Component
public final class BeanUtil {

    private static ApplicationContext context;

    private BeanUtil(ApplicationContext context) {
        BeanUtil.context = context;
    }

    /**
     * Gets bean.
     *
     * @param <T>   the type parameter
     * @param clazz the clazz
     * @return the bean
     * @throws BeansException the beans exception
     */
    public static <T> T getBean(Class<T> clazz) throws BeansException {
        return context.getBean(clazz);
    }
}
