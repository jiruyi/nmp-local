package com.matrictime.network.enums;

public enum DeviceTypeEnum {
    STATION_BASE("00","基站"),
    STATION_INSIDE("01","接入基站"),
    STATION_BOUNDARY("02","边界基站"),

    DEVICE_BASE("10","其他设备"),
    DEVICE_DISPENSER("11","密钥中心"),
    DEVICE_GENERATOR("12","生成机"),
    DEVICE_CACHE("13","缓存机"),

    DATA_BASE("20","数据采集"),
    ACCUSE_CENTER("21","指控中心");
    private String code;
    private String conditionDesc;

    public String getCode() {
        return code;
    }

    public String getConditionDesc() {
        return conditionDesc;
    }

    DeviceTypeEnum(String code, String conditionDesc) {
        this.code = code;
        this.conditionDesc = conditionDesc;
    }
}
