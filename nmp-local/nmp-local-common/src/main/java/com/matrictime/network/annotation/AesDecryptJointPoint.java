package com.matrictime.network.annotation;

import com.matrictime.network.util.AesEncryptUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Aspect
@Component
public class AesDecryptJointPoint {

    private Logger log = LoggerFactory.getLogger(AesEncryJoinPoint.class);

    @AfterReturning(value = "@annotation(com.matrictime.network.annotation.AesDecrypt) && @annotation(aesDecrypt)", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, Object result,AesDecrypt aesDecrypt) throws Throwable {
        log.info("进入解密切面");
        if (null == aesDecrypt|| StringUtils.isEmpty(aesDecrypt.key())){
            return;
        }
        String[] keys = aesDecrypt.key().split(",");

        Field field = result.getClass().getDeclaredField("resultObj");
        field.setAccessible(true);
        Method method = result.getClass().getMethod("getResultObj");
        //通过get方法获取属性值
        Object resultObj = method.invoke(result);
        field.setAccessible(false);


        if(resultObj instanceof ArrayList<?>){
            for (Object o : (List<?>) resultObj) {
                for (String key : keys) {
                    StringBuilder stringBuilder = new StringBuilder(key);
                    String str = key.substring(0,1).toUpperCase(Locale.ROOT);
                    stringBuilder.replace(0,1,str);
                    Field fields = resultObj.getClass().getDeclaredField(key);
                    fields.setAccessible(true);
                    Method objMethod = resultObj.getClass().getMethod("get" + stringBuilder);
                    //通过get方法获取属性值
                    Object value =objMethod.invoke(resultObj);
                    log.info("get方法获取的值为:" + value);
                    if(null != value){
                        String decryptValue = AesEncryptUtil.aesDecrypt(String.valueOf(value));
                        log.info("解密后的值为:"+decryptValue);
                        fields.set(resultObj, decryptValue);
                    }
                    fields.setAccessible(false);
                }
            }
        }else {
            for (String key : keys) {
                StringBuilder stringBuilder = new StringBuilder(key);
                String str = key.substring(0,1).toUpperCase(Locale.ROOT);
                stringBuilder.replace(0,1,str);
                Field fields = resultObj.getClass().getDeclaredField(key);
                fields.setAccessible(true);
                Method objMethod = resultObj.getClass().getMethod("get" + stringBuilder);
                //通过get方法获取属性值
                Object value =objMethod.invoke(resultObj);
                log.info("get方法获取的值为:" + value);
                if(null != value){
                    String decryptValue = AesEncryptUtil.aesDecrypt(String.valueOf(value));
                    log.info("解密后的值为:"+decryptValue);
                    fields.set(resultObj, decryptValue);
                }
                fields.setAccessible(false);
            }
        }

    }

}
