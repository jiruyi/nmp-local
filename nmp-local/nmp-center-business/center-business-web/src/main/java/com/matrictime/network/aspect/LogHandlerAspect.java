package com.matrictime.network.aspect;

import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.annotation.SystemLog;
import com.matrictime.network.context.RequestContext;
import com.matrictime.network.dao.domain.LogDomainService;
import com.matrictime.network.dao.model.NmplOperateLog;
import com.matrictime.network.model.Result;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;


/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project microservicecloud-jzsg
 * @date 2021/9/6 0006 9:27
 * @desc 日志切面处理
 */
@Aspect
@Component
public class LogHandlerAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogHandlerAspect.class);

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private LogDomainService logDomainService;

    @Pointcut("@annotation(com.matrictime.network.annotation.SystemLog)")
    public void controllerAspect() {
    }


    /**
     * @param
     * @return void
     * @title doAfterReturn
     * @description
     * @author jiruyi
     * @create 2021/9/6 0006 11:07
     */
    @AfterReturning(value = "controllerAspect()", returning = "returnValue")
    public void doAfterReturn(JoinPoint joinPoint, Object returnValue) {
        logger.info("进入日志切面后置通知");
        try {
            NmplOperateLog networkLog = NmplOperateLog.builder().build();
            packageModel(joinPoint, networkLog);
            //返回值
            networkLog.setOperRespParam(subStr(returnValue));
            if (!ObjectUtils.isEmpty(returnValue)) {
                Result result = (Result) returnValue;
                networkLog.setIsSuccess(result.isSuccess());
            }
            //日志保存
            logDomainService.saveLog(networkLog);

        } catch (Exception e) {
            logger.info("日志切面后置通知异常:{}", e.getMessage());
        }

    }

    /**
     * @param
     * @return java.lang.String
     * @title subStr
     * @description 对参数截取
     * @author jiruyi
     * @create 2021/9/7 0007 16:49
     */
    public String subStr(Object obj) {
        if (ObjectUtils.isEmpty(obj)) {
            return "";
        }
        String str = JSONObject.toJSONString(obj);
        if (ObjectUtils.isEmpty(str)) {
            return "";
        }
        return str.length() > 999 ? str.substring(0, 999) : str;
    }

    /**
     * @param
     * @return void
     * @title packageModel
     * @description 组转日志参数
     * @author jiruyi
     * @create 2021/9/6 0006 11:11
     */
    public void packageModel(JoinPoint joinPoint, NmplOperateLog networkLog) {
        try {
            /**ip*/
            String ip = ObjectUtils.isEmpty(request.getHeader("X-Real-IP"))
                    ?request.getRemoteAddr():request.getHeader("X-Real-IP");
            networkLog.setSourceIp(ip);
            /**方法*/
            networkLog.setOperMethod(joinPoint.getSignature().getName());
            /**请求url*/
            networkLog.setOperUrl(request.getRequestURI());
            /**入参*/
            networkLog.setOperRequParam(getParamStr(joinPoint));
            /**操作人*/
            networkLog.setOperUserId(RequestContext.getUser().getLoginAccount());
            networkLog.setOperUserName(RequestContext.getUser().getNickName());
            setFromAnnatationParamter(joinPoint, networkLog);
        } catch (Exception e) {
            logger.error("packageModel exception:{}", e.getMessage());
        }
    }

    /**
     * @param
     * @return void
     * @title setFromAnnatationParamter
     * @description 设置注解参数
     * @author jiruyi
     * @create 2021/9/6 0006 11:26
     */
    public static void setFromAnnatationParamter(JoinPoint joinPoint, NmplOperateLog networkLog) {
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            SystemLog systemLog = method.getAnnotation(SystemLog.class);
            /**渠道*/
            networkLog.setChannelType(systemLog.channel());
            /**描述*/
            networkLog.setOperDesc(systemLog.operDesc());
            /**模块*/
            networkLog.setOperModul(systemLog.opermodul());
            /**类型*/
            networkLog.setOperType(systemLog.operType());
            /**级别*/
            networkLog.setOperLevel(systemLog.operLevl());
            /**用户*/
//            if (!ObjectUtils.isEmpty(RequestContext.getUser())) {
//                networkLog.setOperUserId(RequestContext.getUser().getUserId().toString());
//                networkLog.setOperUserName(RequestContext.getUser().getUserName());
//            }
        } catch (Exception e) {
            logger.error("setFromAnnatationParamter exception:{}", e.getMessage());
        }
    }

    /**
     * @param
     * @return java.lang.String
     * @title getParamStr
     * @description
     * @author jiruyi
     * @create 2021/9/7 0007 17:41
     */
    public String getParamStr(JoinPoint joinPoint) {
        //方法 1 请求的方法参数值 JSON 格式 null不显示
        String str = "";
        try {
            if (joinPoint.getArgs().length > 0) {
                for (Object o : joinPoint.getArgs()) {
                    if (o instanceof HttpServletRequest || o instanceof HttpServletResponse) {
                        continue;
                    }
                    str += JSONObject.toJSONString(o);
                    logger.info("请求参数 :" + JSONObject.toJSONString(o));
                }
            }
        }catch (Exception e){
            logger.error("获取参数异常：{}",e.getMessage());
        }
        return str.length() > 999 ? str.substring(0, 999) : str;
    }

}
