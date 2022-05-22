package com.matrictime.network.controller.aop;

import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.api.request.BaseReq;
import com.matrictime.network.api.request.BindReq;
import com.matrictime.network.base.util.ReqUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class RequestAspect {

    /**
     * 表示在执行被@MonitorRequest注解修饰的方法之前 会执行doBefore()方法
     *
     * @param joinPoint 连接点，就是被拦截点
     */
    @Before(value = "@annotation(com.matrictime.network.controller.aop.MonitorRequest)")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        //获取到请求的属性
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //获取到请求对象
        HttpServletRequest request = attributes.getRequest();

        //URL：根据请求对象拿到访问的地址
        log.info("url={},sendMethod={},class={},method={},输入参数={}",
                request.getRequestURL(),
                request.getMethod(),
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                JSONObject.toJSONString(joinPoint.getArgs()));

//        Object args = joinPoint.getArgs()[0];
//        if (args instanceof BaseReq) {
//            ReqUtil reqUtil = new ReqUtil(args);
//            Object o = reqUtil.jsonReqToDto((BaseReq) args);
//            System.out.println(o.hashCode());
////            joinPoint.getArgs()[0] = o;
//            BeanUtils.copyProperties(o,args);
//            System.out.println(joinPoint.getArgs()[0].hashCode());
//        }

    }

//    @After(value = "@annotation(com.matrictime.network.controller.aop.MonitorRequest)")
//    public void doAfter(JoinPoint joinPoint) throws Throwable {
//
//    }

    @AfterReturning(value = "@annotation(com.matrictime.network.controller.aop.MonitorRequest)", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, Object result) throws Throwable {
        log.info("class={},method={},返回值={}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                JSONObject.toJSONString(result));
    }

}
