package com.matrictime.network.base.enums;

public enum LoginStatusEnum {
    NORMAL("1","正常"),
    UPDATE("2","被修改");

    private String code;
    private String conditionDesc;
    public String getCode() {
        return code;
    }

    public String getConditionDesc() {
        return conditionDesc;
    }

    LoginStatusEnum(String code, String conditionDesc) {
        this.code = code;
        this.conditionDesc = conditionDesc;
    }

}
