package com.matrictime.network.base.enums;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project microservicecloud-jzsg
 * @date 2021/9/6 0006 14:45
 * @desc
 */
public enum LoginTypeEnum {

    PASSWORD("1","用户名验证"),
    MOBILEPHONE("2","手机电话");

    private String code;
    private String conditionDesc;
    public String getCode() {
        return code;
    }

    public String getConditionDesc() {
        return conditionDesc;
    }

    LoginTypeEnum(String code, String conditionDesc) {
        this.code = code;
        this.conditionDesc = conditionDesc;
    }

}
