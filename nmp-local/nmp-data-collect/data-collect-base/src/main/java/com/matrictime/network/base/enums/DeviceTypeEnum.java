package com.matrictime.network.base.enums;

public enum DeviceTypeEnum {
    BASE_STATION("01","接入基站"),
    BORDER_BASE_STATION("02","边界基站"),
    BASE("10","其他设备"),
    DISPENSER("11","密钥中心"),
    GENERATOR("12","生成机"),
    CACHE("13","缓存机"),
    DAT_COLLECT("20","数据采集设备"),
    COMMAND_CENTER("21","指令控制中心");
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
