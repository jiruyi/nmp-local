package com.matrictime.network.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project microservicecloud-jzsg
 * @date 2021/9/6 0006 10:02
 * @desc service日志注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.PARAMETER})
public @interface SystemLog {

    /**
      * 模块
      */
    String opermodul()  default "";

    /**
     * 类型
     */
    String operType() default "";
    /**
     * 描述
     */
    String operDesc() default "";

    /**
     * 级别
     */
    String operLevl() default "4";

    /**
     * 渠道
     */
    byte channel() default  1;
}
