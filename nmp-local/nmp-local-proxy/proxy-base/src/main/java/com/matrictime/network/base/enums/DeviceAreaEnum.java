package com.matrictime.network.base.enums;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project microservicecloud-jzsg
 * @date 2021/9/16 0016 18:59
 * @desc
 */
public enum DeviceAreaEnum {

    SAMEAREA(1,"同省市区"),
    SAMECITY(2,"同省同市不同区"),
    SAMEPROVINCE(3,"同省不同市"),
    DIFFROVINCE(4,"不同省");

    private int code;
    private String conditionDesc;
    public int getCode() {
        return code;
    }

    public String getConditionDesc() {
        return conditionDesc;
    }

    DeviceAreaEnum(int code, String conditionDesc) {
        this.code = code;
        this.conditionDesc = conditionDesc;
    }
}
