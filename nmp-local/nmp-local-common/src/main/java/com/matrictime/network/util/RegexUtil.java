package com.matrictime.network.util;

import java.util.regex.Pattern;

/**
 * 正则处理工具类
 */
public class RegexUtil {
    /**
     * 正整数正则
     */
    public static final Pattern P_POSITIVE_INTEGER = Pattern.compile("^[1-9]+\\d*$");

    /**
     * 0和正整数
     */
    public static final Pattern P_INTEGER = Pattern.compile("^[0-9]+\\d*$");


    /**
     *  匹配日期格式：yyyyMMdd
     */
    public static final Pattern P_TIMEREGEX = Pattern.compile("(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})(((0[13578]|1[02])(0[1-9]|[12][0-9]|3[01]))|"+
            "((0[469]|11)(0[1-9]|[12][0-9]|30))|(02(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|"+
            "((0[48]|[2468][048]|[3579][26])00))0229)$");

    /**
     * 简单的手机号正则校验 1开头的11数字
     */
    public static final Pattern P_MOBILE_TEL = Pattern.compile("^1[0-9]{10}$");

    /**
     * 正整数或1位小数
     */
    public static final Pattern P_INTEGER_1_POINT = Pattern.compile("^[1-9][0-9]*(\\.[0-9]{1})?$");

    /**
     * 登陆模块密码正则，8-32位英文数字和下划线
     */
    public static final Pattern P_LOGIN_PASSWORD = Pattern.compile("^[a-zA-Z\\d]{8,32}$");

    /**
     * 键盘输入ASCII
     */
    public static final Pattern P_KEYBOARD = Pattern.compile("^[\\x00-\\xFF]+$");

    public static final Pattern P_HORIZONTAL_LINE = Pattern.compile("-");

    public static final Pattern P_NUMERIC = Pattern.compile("^(\\-|\\+)?\\d+(\\.\\d+)?$");





}
