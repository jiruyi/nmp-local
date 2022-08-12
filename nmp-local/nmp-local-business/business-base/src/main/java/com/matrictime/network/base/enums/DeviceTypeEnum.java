package com.matrictime.network.base.enums;

public enum DeviceTypeEnum {
    BASE("10","其他设备"),
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

    DeviceTypeEnum(String code, String conditionDesc) {
        this.code = code;
        this.conditionDesc = conditionDesc;
    }
}
