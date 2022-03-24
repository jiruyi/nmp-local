package com.matrictime.network.base.enums;

public enum DeviceTypeEnum {
    DISPENSER("03","密钥分发机"),
    GENERATOR("04","生成机"),
    CACHE("05","缓存机");
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
