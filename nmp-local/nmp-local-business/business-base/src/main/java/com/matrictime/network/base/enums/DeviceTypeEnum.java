package com.matrictime.network.base.enums;

public enum DeviceTypeEnum {
    DISPENSER("01","密钥分发机"),
    GENERATOR("02","生成机"),
    CACHE("03","缓存机");
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
