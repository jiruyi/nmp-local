package com.matrictime.network.base.enums;

public enum ProxyConfigEnum {
    REDIS_IP("1000","redis ip"),
    REDIS_PASSWORD("1002","redis password"),
    LOCAL_IP("1001","local ip");


    private String code;
    private String desc;

    ProxyConfigEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }


    public String getDesc() {
        return desc;
    }
}
