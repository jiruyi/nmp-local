package com.matrictime.network.base.enums;

public enum ConfigModeEnum {
    SECRET_CONFIG(1,"密区配置"),
    COMM_CONFIG(2,"通信配置"),
    STATION_CONFIG(3,"基站配置"),
    CA_CONFIG(4,"CA配置"),
    DNS_CONFIG(5,"DNS配置")
    ;

    ConfigModeEnum(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    /**
     * 策略id
     */
    private Integer id;

    /**
     * 策略描述
     */
    private String description;

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

}
