package com.matrictime.network.base.enums;

/**
 * @author by wangqiang
 * @date 2023/9/22.
 */
public enum BusinessRouteEnum {

    DISPENSER("11","根密钥中心"),

    ALLEGATION_CENTER("31","指控中心"),

    BILLING_CENTER("41","计费中心");

    private String code;

    private String conditionDesc;

    public String getCode() {
        return code;
    }

    public String getConditionDesc() {
        return conditionDesc;
    }

    BusinessRouteEnum(String code, String conditionDesc) {
        this.code = code;
        this.conditionDesc = conditionDesc;
    }
}
