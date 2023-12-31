package com.matrictime.network.constant;


public class RegExpConstants {

    /**
     * @desc 大陆手机号正则表达式
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
     * 此方法中前三位格式有：
     * 13+任意数
     * 144,145,147,149
     * 15+除4的任意数(不要写^4，这样的话字母也会被认为是正确的)
     * 166
     * 17+3,5,6,7,8
     * 18+任意数
     * 198,199
     */
    public static final String REG_EXP_CHINA_PHONE = "^((13[0-9])|(14[4,5,7,9])|(15[0-3,5-9])|(166)|(17[3,5,6,7,8])|(18[0-9])|(19[8,9]))\\d{8}$";

    /**
     * @desc 香港手机号校验
     * 香港手机号码8位数，5|6|8|9开头+7位任意数
     */
    public static final String REG_EXP_HK_PHONE = "^(5|6|8|9)\\d{7}$";

    /**
     * @desc ipv4地址校验
     * ipv4的ip地址都是（0~255）.（0~255）.（0~255）.（0~255）的格式
     */
    public static final String REG_EXP_IPV4 ="^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";

    public static final String REG_EXP_IPV6 ="/^([\\da-fA-F]{1,4}:){6}((25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}(25[0-5]|2[0-4]\\d|[01]?\\d\\d?)$|^::([\\da-fA-F]{1,4}:){0,4}((25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}(25[0-5]|2[0-4]\\d|[01]?\\d\\d?)$|^([\\da-fA-F]{1,4}:):([\\da-fA-F]{1,4}:){0,3}((25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}(25[0-5]|2[0-4]\\d|[01]?\\d\\d?)$|^([\\da-fA-F]{1,4}:){2}:([\\da-fA-F]{1,4}:){0,2}((25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}(25[0-5]|2[0-4]\\d|[01]?\\d\\d?)$|^([\\da-fA-F]{1,4}:){3}:([\\da-fA-F]{1,4}:){0,1}((25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}(25[0-5]|2[0-4]\\d|[01]?\\d\\d?)$|^([\\da-fA-F]{1,4}:){4}:((25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}(25[0-5]|2[0-4]\\d|[01]?\\d\\d?)$|^([\\da-fA-F]{1,4}:){7}[\\da-fA-F]{1,4}$|^:((:[\\da-fA-F]{1,4}){1,6}|:)$|^[\\da-fA-F]{1,4}:((:[\\da-fA-F]{1,4}){1,5}|:)$|^([\\da-fA-F]{1,4}:){2}((:[\\da-fA-F]{1,4}){1,4}|:)$|^([\\da-fA-F]{1,4}:){3}((:[\\da-fA-F]{1,4}){1,3}|:)$|^([\\da-fA-F]{1,4}:){4}((:[\\da-fA-F]{1,4}){1,2}|:)$|^([\\da-fA-F]{1,4}:){5}:([\\da-fA-F]{1,4})?$|^([\\da-fA-F]{1,4}:){6}:$/";

    /**
     * @desc port端口校验
     * port端口号校验（1-65535）
     */
    public static final String REG_EXP_PORT = "^([1-9]\\d{0,3}|[1-5]\\d{4}|6[0-4]\\d{3}|65[0-4]\\d{2}|655[0-2]\\d|6553[0-5])$";

    /**
     * @desc 邮箱校验
     */
    public static final String REG_EXP_EMAIL = "^[0-9a-zA-Z_.-]+[@][0-9a-zA-Z_.-]+([.][a-zA-Z]+){1,2}$";



    /*******************************************匹配前端校验规则*******************************************************/

    /**
     * @desc 用户号
     */
    public static final String REGEX_LOGACCOUNT = "^[a-zA-Z0-9~!@#$%^&*()_+]*$";

    /**
     * @desc 手机号
     */
    public static final String REGEX_PHONE = "^((13|14|15|16|17|18|19)[0-9]{1}\\d{8})$";

    /**
     * @desc 密码
     */
    public static final String REGEX_PASSWORD = "^(?![A-Za-z]+$)(?![A-Z0-9]+$)(?![a-z0-9]+$)(?![a-z~!@#$%^&*()_+]+$)(?![A-Z~!@#$%^&*()_+]+$)(?![0-9~!@#$%^&*()_+]+$)[a-zA-Z0-9~!@#$%^&*()_+]{8,12}$";

    /**
     * @desc 设备号
     */
    public static final String REGEX_DEVICE_ID = "^[0-9]{0,9}$";

    /**
     * @desc 座机号
     */
    public static final String REGEX_LAND_LINE ="^(0[0-9]{2,3}-[0-9]{8})|(400[0-9]{7})$";



}
