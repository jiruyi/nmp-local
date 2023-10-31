package com.matrictime.network.base.exception;


/**
 * @description
 * @author jiruyi@jzsg.com.cn
 * @create 2021/7/14 16:49
 */
public class ErrorCode {


    /**
     * 系统异常
     */
    public static final String SYSTEM_ERROR = "-1";

    /**
     * 数据库异常
     */
    public static final String DATABASE_ERROR = "10001";

    /**
     * 入参
     */
    public static final String PARAM_EXCEPTION = "10002";

    /**
     * 信令已开启追踪
     */
    public static final String SIGNAL_I_EXCEPTION = "signal_10000";

    /**
     * 信令关闭参数校验有误
     */
    public static final String SIGNAL_O_EXCEPTION = "signal_10001";

}
