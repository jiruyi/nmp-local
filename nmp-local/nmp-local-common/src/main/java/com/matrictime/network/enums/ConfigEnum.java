package com.matrictime.network.enums;

public enum ConfigEnum {
    COLLECT_PERIOD("20000","采集周期"),
    REPORT_PERIOD("20001","上报周期");
    private String code;
    private String name;

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    ConfigEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
