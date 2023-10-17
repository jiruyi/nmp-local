package com.matrictime.network.enums;

public enum CompanyEnum {

    OPERATOR("00","运营商"),

    BIG_AREA("01","大区"),

    SMALL_AREA("02","小区");

    private String code;
    private String conditionDesc;

    public String getCode() {
        return code;
    }

    public String getConditionDesc() {
        return conditionDesc;
    }

    CompanyEnum(String code, String conditionDesc) {
        this.code = code;
        this.conditionDesc = conditionDesc;
    }
}
