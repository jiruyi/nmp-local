package com.matrictime.network.base.enums;

/**
 * 设备枚举类
 */
public enum DeviceGenTypeEnum {

    INSIDE("01","小区内基站"),
    BOUNDARY("02","小区边界基站"),
    DISPENSER("11","密钥中心"),
    GENERATOR("12","生成机"),
    CACHE("13","缓存机");

    private String code;
    private String conditionDesc;

    public String getCode() {
        return code;
    }

    public String getConditionDesc() {
        return conditionDesc;
    }

    DeviceGenTypeEnum(String code, String conditionDesc) {
        this.code = code;
        this.conditionDesc = conditionDesc;
    }
}
