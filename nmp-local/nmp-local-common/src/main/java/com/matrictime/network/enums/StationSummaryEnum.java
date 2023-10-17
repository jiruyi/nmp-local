package com.matrictime.network.enums;

/**
 * @author by wangqiang
 * @date 2023/8/17.
 */
public enum StationSummaryEnum {

    TOTAL_NET_WORKS("12","网络总数"),

    KET_CENTER("11","密钥中心"),

    BORDER_BASE_STATION("02","小区边界基站"),

    BASE_STATION("01","小区内基站");

    private String code;
    private String conditionDesc;

    public String getCode() {
        return code;
    }

    public String getConditionDesc() {
        return conditionDesc;
    }

    StationSummaryEnum(String code, String conditionDesc) {
        this.code = code;
        this.conditionDesc = conditionDesc;
    }
}
