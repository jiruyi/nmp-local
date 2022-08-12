package com.matrictime.network.base.enums;

public enum StationTypeEnum {

    BASE("00","基站设备"),
    BOUNDARY("02","小区边界基站"),
    INSIDE("01","小区内基站");
    private String code;
    private String conditionDesc;
    public String getCode() {
        return code;
    }

    public String getConditionDesc() {
        return conditionDesc;
    }

    StationTypeEnum(String code, String conditionDesc) {
        this.code = code;
        this.conditionDesc = conditionDesc;
    }
}
