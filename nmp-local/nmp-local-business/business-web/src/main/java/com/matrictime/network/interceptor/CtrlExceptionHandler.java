package com.matrictime.network.interceptor;

import com.alibaba.fastjson.JSON;
import com.matrictime.network.model.Result;
import com.sun.jersey.core.util.Priority;
import org.apache.catalina.connector.Response;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常处理类
 */
@Priority(1)
@ControllerAdvice
public class CtrlExceptionHandler {
    private static Logger logger = LoggerFactory.getLogger(CtrlExceptionHandler.class);

    //拦截未授权页面
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public String handleException(UnauthorizedException e) {
        logger.debug(e.getMessage());
        Result result =new Result();
        result.setSuccess(false);
        result.setErrorMsg("该用户无权限进行此项操作");
        result.setErrorCode("401");
        return JSON.toJSONString(result);
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler(AuthorizationException.class)
    @ResponseBody
    public String handleException2(AuthorizationException e) {
        logger.debug(e.getMessage());
        Result result =new Result();
        result.setSuccess(false);
        result.setErrorMsg("登录已经失效，请重新登录");
        result.setErrorCode("403");
        return JSON.toJSONString(result);
    }

}
