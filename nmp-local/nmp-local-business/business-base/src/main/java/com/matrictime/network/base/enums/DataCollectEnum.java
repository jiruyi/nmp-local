package com.matrictime.network.base.enums;

public enum DataCollectEnum {

    ONLINE_USER("10000","在线用户数","人"),
    USED_KEY("10001","使用秘钥量","byte"),
    DOWNLOAD_KEY("10002","下载秘钥量","byte"),
    REMAINED_KEY("10003","剩余秘钥量","byte"),
    INNER_NET_LAOD("10004","内网宽带负载","bps"),
    OUTER_NET_LOAD("10005","外网宽带负载","bps"),
    BANDWITH("10006","带宽","bps"),
    STORE_KEY("10007","秘钥存储量","byte");
    private String code;
    private String conditionDesc;

    private String unit;

    public String getCode() {
        return code;
    }

    public String getConditionDesc() {
        return conditionDesc;
    }

    public String getUnit(){return unit;}

    DataCollectEnum(String code, String conditionDesc, String unit) {
        this.code = code;
        this.conditionDesc = conditionDesc;
        this.unit = unit;
    }
}
