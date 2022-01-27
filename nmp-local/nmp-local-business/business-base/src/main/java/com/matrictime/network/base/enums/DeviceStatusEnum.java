package com.matrictime.network.base.enums;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project microservicecloud-jzsg
 * @date 2021/9/13 0013 11:05
 * @desc
 */
public enum DeviceStatusEnum {

    NORMAL("1","审核通过"),
    DISABLE("2","禁用"),
    OFFLINE("3","下线"),
    NOAUDIT("4","待审核"),
    ACTIVE("5","激活"),
    REJECT("6","审核拒绝");
    private String code;
    private String conditionDesc;
    public String getCode() {
        return code;
    }

    public String getConditionDesc() {
        return conditionDesc;
    }

    DeviceStatusEnum(String code, String conditionDesc) {
        this.code = code;
        this.conditionDesc = conditionDesc;
    }
}
