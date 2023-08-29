package com.matrictime.network.base.enums;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/8/29 0029 10:05
 * @desc
 */
public enum BusinessDataEnum {
    AlarmInfo("alarm_info","nmpl_alarm_info");

    private String businessCode;

    private String tableName;

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    BusinessDataEnum(String businessCode, String tableName) {
        this.businessCode = businessCode;
        this.tableName = tableName;
    }
}
