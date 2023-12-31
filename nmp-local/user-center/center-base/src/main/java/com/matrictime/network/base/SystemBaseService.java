package com.matrictime.network.base;

import com.matrictime.network.exception.ErrorCode;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.model.Result;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jiruyi@jzsg.com.cn
 * @project eps
 * @date 2021/7/15 9:18
 * @desc
 */
public class SystemBaseService {

    private static final Logger logger = LoggerFactory.getLogger(SystemBaseService.class);

    /**
     * @param
     * @return
     * @description 构建成功返回结果
     * @author jiruyi@jzsg.com.cn
     * @create 2021/7/15 9:23
     */
    public <T> Result<T> buildResult(T response){

        Result<T> r = new Result<T>();

        r.setSuccess(true);

        r.setResultObj(response);
        return r;
    }

    public <T> Result<T> buildResult(T response,String errCode ,String errorMessage){

        Result<T> r = new Result<T>();

        r.setSuccess(true);

        r.setResultObj(response);
        r.setErrorCode(errCode);
        r.setErrorMsg(errorMessage);
        return r;
    }

    public <T> Result<T> buildResult(T response,String errCode ,String errorMessage,String extendMsg){

        Result<T> r = new Result<T>();

        r.setSuccess(true);

        r.setResultObj(response);
        r.setErrorCode(errCode);
        r.setErrorMsg(errorMessage);
        r.setExtendMsg(extendMsg);
        return r;
    }



    /**
     * @param
     * @return
     * @description This is a method
     * @author jiruyi@jzsg.com.cn
     * @create 2021/8/9 9:59
     */
    public <T> Result<T> failResult(  String errCode ,String errorMessage ) {
        Result<T> result = new Result<T>();
        result.setSuccess(false);
        result.setErrorCode(errCode);
        result.setErrorMsg(errorMessage);
        return result;
    }

    public <T> Result<T> failResult(Exception exception) {
        Result<T> result = new Result<T>();
        result.setSuccess(false);
        result.setErrorMsg(exception.getMessage());
        return result;
    }

    public <T> Result<T> failResult(String errorMessage) {
        Result<T> result = new Result<T>();
        result.setSuccess(false);
        if (StringUtils.isBlank(errorMessage)) {
            result.setErrorMsg(ErrorMessageContants.SYSTEM_ERROR_MSG);
            result.setErrorCode(ErrorCode.SYSTEM_ERROR);
        }else {
            result.setErrorMsg(errorMessage);
        }

        return result;
    }

}
