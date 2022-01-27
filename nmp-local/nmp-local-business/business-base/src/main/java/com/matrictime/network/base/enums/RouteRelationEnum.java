package com.matrictime.network.base.enums;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project microservicecloud-jzsg
 * @date 2021/9/15 0015 15:22
 * @desc
 */
public enum RouteRelationEnum {
    PATERNITY("1","父子关系"),
    PEER("2","同级关系");

    private String code;
    private String conditionDesc;
    public String getCode() {
        return code;
    }

    public String getConditionDesc() {
        return conditionDesc;
    }

    RouteRelationEnum(String code, String conditionDesc) {
        this.code = code;
        this.conditionDesc = conditionDesc;
    }
}
