package com.matrictime.network.enums;

/**
 * 链路枚举
 */
public enum LinkEnum {
    BB("01","边界基站-边界基站"),
    AK("02","接入基站-密钥中心"),
    BK("03","边界基站-密钥中心"),
    DB("04","采集系统-边界基站");

    private String code;
    private String conditionDesc;
    public String getCode() {
        return code;
    }

    public String getConditionDesc() {
        return conditionDesc;
    }

    LinkEnum(String code, String conditionDesc) {
        this.code = code;
        this.conditionDesc = conditionDesc;
    }
}
