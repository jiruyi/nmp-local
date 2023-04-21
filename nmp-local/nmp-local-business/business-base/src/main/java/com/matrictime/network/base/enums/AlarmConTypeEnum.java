package com.matrictime.network.base.enums;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/4/21 0021 14:25
 * @desc
 */
public enum AlarmConTypeEnum {
    CPU("1","cpu过高"),
    MEM("2","内存不足"),
    DISK("3","磁盘不足"),
    FLOW("4","流量过载");
    private String code;
    private String conditionDesc;
    public String getCode() {
        return code;
    }

    public String getConditionDesc() {
        return conditionDesc;
    }

    AlarmConTypeEnum(String code, String conditionDesc) {
        this.code = code;
        this.conditionDesc = conditionDesc;
    }
}
