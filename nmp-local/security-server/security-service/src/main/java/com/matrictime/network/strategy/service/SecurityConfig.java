package com.matrictime.network.strategy.service;

import com.matrictime.network.strategy.annotation.ConfigMode;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

public class SecurityConfig implements ApplicationContextAware {
    private ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    protected static Map<Integer,ConfigService> configServiceMap = new HashMap<>();

    @PostConstruct
    public void init() {
        Map<String, Object> strategyModeMap = applicationContext.
                getBeansWithAnnotation(ConfigMode.class);
        strategyModeMap.entrySet().forEach(r->{
            ConfigMode configMode = AnnotationUtils.findAnnotation(r.getValue().getClass(), ConfigMode.class);
            if(r.getValue() instanceof ConfigService){
                configServiceMap.put(configMode.configMode().getId(), (ConfigService) r.getValue());
            }
        });
    }

}
