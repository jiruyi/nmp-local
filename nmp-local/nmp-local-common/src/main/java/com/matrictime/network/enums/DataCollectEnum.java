package com.matrictime.network.enums;

import java.util.HashMap;
import java.util.Map;

public enum DataCollectEnum {
    ACCESS_BANDWITH("10018","接入带宽","byte"),
    INTERVAL_BANDWITH("10019","区间带宽","byte"),
    ACCESS_LOAD("10020","接入负载","byte"),
    COMM_LOAD_UP_FLOW("10010","通信负载上行流量","byte"),
    COMM_LOAD_DOWN_FLOW("10011","通信负载下行流量","byte"),
    CROSS_ZONE_LOAD("10021","转发负载","byte");


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
            put("10010",COMM_LOAD_UP_FLOW);
            put("10011",COMM_LOAD_DOWN_FLOW);
        }
    };

    public static Map<String,DataCollectEnum> getMap(){
        return map;
    }

}
