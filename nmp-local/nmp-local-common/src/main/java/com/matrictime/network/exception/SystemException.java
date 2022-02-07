package com.matrictime.network.exception;

/**
 * @author jiruyi@jzsg.com.cn
 * @project eps
 * @date 2021/7/14 16:57
 * @desc
 */
public class SystemException extends RuntimeException {

    /**
     * 通用异常
     */
    public String code ;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public SystemException() {
        super();
    }

    public SystemException(String message, Throwable cause, boolean enableSuppression,
                           boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public SystemException(Throwable cause) {
        super(cause);
    }

    public SystemException(String message) {
        super(message);
    }

    public SystemException(String code, String message) {
        super(message);
        this.code = code;
    }

    public SystemException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    @Override
    public String toString() {
        return "BaseIllegalException [code=" + code + ", getMessage()=" + getMessage() + "]";
    }
}
