package com.matrictime.network.base.enums;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project microservicecloud-jzsg
 * @date 2021/9/13 0013 11:05
 * @desc
 */
public enum DeviceStatusEnum {

    NORMAL("01","静态"),
    ACTIVE("02","激活"),
    NOAUDIT("03","禁用"),
    OFFLINE("04","下线"),
    REJECT("05","审核拒绝");
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
