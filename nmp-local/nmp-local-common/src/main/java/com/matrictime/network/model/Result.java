package com.matrictime.network.model;

import java.io.Serializable;

/**
 * @Authth ruyi.ji
 * @Desc:
 * @Date: 2020-10-22 14:05
 * @VERSION：
 */
public class Result<T> implements Serializable {


    private static final long serialVersionUID = -1578381601651577532L;
    /**
     * 是否成功。
     */
    private boolean success = true;


    /**
     * 对象实例。
     */
    private T resultObj;

    /**
     * 错误编码
     */
    private String errorCode;

    /**
     * 错误描述
     */
    private String errorMsg;

    /**
     * 附加信息
     */
    private String extendMsg;

    /*
     * 构造函数。
     */
    public Result() {

    }

    public Result(boolean success, T resultObj, String code, String msg) {
        this.success = success;
        this.resultObj = resultObj;
        this.errorCode = code;
        this.errorMsg = msg;
    }
    public Result(boolean success, String msg) {
        this.success = success;
        this.errorMsg = msg;
    }

    public Result(boolean success, T resultObj, String errorCode, String errorMsg, String extendMsg) {
        this.success = success;
        this.resultObj = resultObj;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.extendMsg = extendMsg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getResultObj() {
        return resultObj;
    }

    public void setResultObj(T resultObj) {
        this.resultObj = resultObj;
    }
    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getExtendMsg() {
        return extendMsg;
    }

    public void setExtendMsg(String extendMsg) {
        this.extendMsg = extendMsg;
    }

    @Override
    public String toString() {
        return "Result{" +
                "success=" + success +
                ", resultObj=" + resultObj +
                ", errorCode='" + errorCode + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                ", extendMsg='" + extendMsg + '\'' +
                '}';
    }
}