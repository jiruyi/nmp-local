package com.matrictime.network.base.enums;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project microservicecloud-jzsg
 * @date 2021/9/16 0016 18:59
 * @desc
 */
public enum SystemTypeEnum {

    YUNYING("1","运营系统"),
    YUNWEI("2","运维系统"),
    YUNKONG("3","运控系统");

    private String code;
    private String conditionDesc;
    public String getCode() {
        return code;
    }

    public String getConditionDesc() {
        return conditionDesc;
    }

    SystemTypeEnum(String code, String conditionDesc) {
        this.code = code;
        this.conditionDesc = conditionDesc;
    }
}
