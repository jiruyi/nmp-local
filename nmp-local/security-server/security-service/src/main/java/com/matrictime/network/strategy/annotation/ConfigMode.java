package com.matrictime.network.strategy.annotation;

import com.matrictime.network.base.enums.ConfigModeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigMode {


    /**
     * 配置策略模型
     */
    ConfigModeEnum configMode();
}
