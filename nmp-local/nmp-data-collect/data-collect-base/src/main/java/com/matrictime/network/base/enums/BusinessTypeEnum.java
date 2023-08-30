package com.matrictime.network.base.enums;

public enum BusinessTypeEnum {
    TERMINAL_USER("1000","终端用户采集"),
    BUSINESS_HEART("1002","业务心跳采集"),
    DATA_TRAFFIC("1003","数据流量采集"),
    COMMUNITY_INFO("1004","小区信息采集"),
    COMMUNITY_HEART("1005","小区心跳采集"),
    STATION_NUMBER("1006","基站总数采集"),
    BORDER_STATION_DATA("1007","边界基站数据采集"),
    KEY_CENTER_DATA("1008","密钥中心数据采集"),
    ALARM_DATA("1009","告警数据采集");

    /**
     * 业务类型
     */
    private String code;
    /**
     * 业务描述
     */
    private String description;


    BusinessTypeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
