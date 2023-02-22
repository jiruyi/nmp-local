package com.matrictime.network.annotation;

import com.matrictime.network.util.AesEncryptUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
@Aspect
@Component
public class AesEncryJoinPoint {

    private Logger logger = LoggerFactory.getLogger(AesEncryJoinPoint.class);

    @Pointcut("@annotation(com.matrictime.network.annotation.AesEncry)")
    public void aopPoint() {
    }

    @Around("aopPoint() && @annotation(aesEncry)")
    public Object doEncry(ProceedingJoinPoint jp, AesEncry aesEncry) throws Throwable {
        logger.info("进入加密切面");
        if(aesEncry.key().isEmpty()){
            return  jp.proceed();
        }
        List<String> keys = Arrays.asList(aesEncry.key().split(","));
        Object target = jp.getArgs()[0];
        // 通过反射加密部分入参
        try {
            for (String key : keys) {
                Field field = target.getClass().getDeclaredField(key);
                field.setAccessible(true);
                String type = field.getGenericType().getTypeName();
                logger.info("当前类型为:" + type );
                //获取属性名称
                String name = field.getName();
                StringBuilder stringBuilder = new StringBuilder(name);
                String str = name.substring(0,1).toUpperCase(Locale.ROOT);
                stringBuilder.replace(0,1,str);
                logger.info("当前的属性名称为:" + stringBuilder);
                if(type.endsWith("String")){
                    Method method = target.getClass().getMethod("get" + stringBuilder);
                    //通过get方法获取属性值
                    Object value = method.invoke(target);
                    logger.info("get方法获取的值为:" + value);
                    if(null != value){
                        String encryValue = AesEncryptUtil.aesEncrypt(String.valueOf(value));
                        logger.info("加密后的值为："+encryValue);
                        field.set(target, encryValue);
                    }
                    field.setAccessible(false);
                }
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }finally {
            return jp.proceed();
        }
    }
}