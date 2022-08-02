package com.matrictime.network.base.enums;

public enum AddUserRequestEnum {
    TOBECERTIFIED("1","待认证"),
    AGREE("2","同意"),
    REFUSE("3","拒绝");

    private String code;

    private String conditionDesc;

    public String getCode() {
        return code;
    }

    public String getConditionDesc() {
        return conditionDesc;
    }

    AddUserRequestEnum(String code, String conditionDesc) {
        this.code = code;
        this.conditionDesc = conditionDesc;
    }
}
