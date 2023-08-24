package com.matrictime.network.enums;

import java.util.HashMap;
import java.util.Map;

public enum DataCollectEnum {

    ONLINE_USER("10000","在线用户数","人"),
    USED_KEY("10001","使用秘钥量","byte"),
    DOWNLOAD_KEY("10002","下载秘钥量","byte"),
    REMAINED_KEY("10003","剩余秘钥量","byte"),
    INNER_NET_LAOD("10004","内网宽带负载","bps"),
    OUTER_NET_LOAD("10005","外网宽带负载","bps"),
    BANDWITH("10006","带宽","bps"),
    STORE_KEY("10007","秘钥存储量","byte"),
    FORWARD_LOAD_UP_FLOW("10008","转发负载上行流量","byte"),
    FORWARD_LOAD_DOWN_FLOW("10009","转发负载下行流量","byte"),
    COMM_LOAD_UP_FLOW("10010","通信负载上行流量","byte"),
    COMM_LOAD_DOWN_FLOW("10011","通信负载下行流量","byte"),
    ISOLATIONLOAD_UP_FLOW("10012","隔离板负载上行流量","byte"),
    ISOLATIONLOAD_DOWN_FLOW("10013","隔离板负载下行流量","byte"),
    KEY_RELAY_UP_LOAD("10014","秘钥中继上行负载","byte"),
    KEY_RELAY_DOWN_LOAD("10015","秘钥中继下行负载","byte"),
    KEY_DISTRIBUTE_UP_LOAD("10016","秘钥分发上行负载","byte"),
    KEY_DISTRIBUTE_DOWN_LOAD("10017","秘钥分发下行负载","byte"),
    ACCESS_BANDWITH("10018","接入带宽","byte"),
    ACCESS_LOAD("10020","接入负载","byte"),
    CROSS_ZONE_LOAD("10021","转发负载","byte"),
    INTERVAL_BANDWITH("10019","区间带宽","byte");


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

    private static Map<String,DataCollectEnum> map = new HashMap<String,DataCollectEnum>(){
        {
            put("10008",FORWARD_LOAD_UP_FLOW);
            put("10009",FORWARD_LOAD_DOWN_FLOW);
            put("10010",COMM_LOAD_UP_FLOW);
            put("10011",COMM_LOAD_DOWN_FLOW);
            put("10012",ISOLATIONLOAD_UP_FLOW);
            put("10013",ISOLATIONLOAD_DOWN_FLOW);
            put("10014",KEY_RELAY_UP_LOAD);
            put("10015",KEY_RELAY_DOWN_LOAD);
            put("10016",KEY_DISTRIBUTE_UP_LOAD);
            put("10017",KEY_DISTRIBUTE_DOWN_LOAD);
            put("10018",ACCESS_BANDWITH);
            put("10019",INTERVAL_BANDWITH);
            put("10020",ACCESS_LOAD);
            put("10021",CROSS_ZONE_LOAD);
        }
    };

    public static Map<String,DataCollectEnum> getMap(){
        return map;
    }

}
