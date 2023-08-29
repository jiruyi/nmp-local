package com.matrictime.network.strategy;

import com.matrictime.network.service.BusinessDataService;

import com.matrictime.network.strategy.annotation.BusinessType;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

public class BusinessConifg implements ApplicationContextAware {

    private ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    protected static Map<String, BusinessDataService> configServiceMap = new HashMap<>();

    @PostConstruct
    public void init() {
        Map<String, Object> strategyModeMap = applicationContext.
                getBeansWithAnnotation(BusinessType.class);
        strategyModeMap.entrySet().forEach(r->{
            BusinessType businessType = AnnotationUtils.findAnnotation(r.getValue().getClass(), BusinessType.class);
            if(r.getValue() instanceof BusinessDataService){
                configServiceMap.put(businessType.businessType().getCode(), (BusinessDataService) r.getValue());
            }
        });
    }



}
