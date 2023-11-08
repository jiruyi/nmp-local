package com.matrictime.network.base.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ObjectUtils;

import java.util.Map;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/8/29 0029 15:13
 * @desc
 */
public class SpringContextUtils {

    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtils.applicationContext = applicationContext;
    }

    /**
     * 获取Spring的上下文
     * @return
     */
    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }

    /**
     * 获取Spring上下文容器的Bean名称
     * @return
     */
    public static String[] getBeanDefinitionNames(){
        return applicationContext.getBeanDefinitionNames();
    }

    /**
     * 根据Bean的名称获取Bean
     * @param name Bean名称
     * @param clazz 需要获取的实体
     * @param <T>
     * @return
     */
    public static <T> T getBean(String name,Class<T> clazz){
        return applicationContext.getBean(name,clazz);
    }

    /**
     * 根据Bean的名称获取Bean
     * @param name
     * @return
     */
    public static  Object getBean(String name) throws Exception {
        if(ObjectUtils.isEmpty(applicationContext)){
            throw  new Exception(" applicationContext is null");
        }
        return applicationContext.getBean(name);
    }

    /**
     * 根据class获取Bean
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz){
        return applicationContext.getBean(clazz);
    }

    /**
     * 根据Bean名称获取Class
     * @param name
     * @return
     */
    public static Class<?> getType(String name){
        return applicationContext.getType(name);
    }

    /**
     * 根据类型获取实现类
     * @param classes
     * @return
     */
    public static <T>  Map<String,T> getBeansOfType(Class<T> classes){
        return applicationContext.getBeansOfType(classes);
    }
}
