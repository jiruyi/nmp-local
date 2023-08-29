package com.matrictime.network.base.enums;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/8/29 0029 10:05
 * @desc
 */
public enum BusinessDataEnum {

    BorderStation("border_station","nmpl_base_station_info"),

    Station("station","nmpl_base_station_info"),

    Device("device","nmpl_device_info"),

    CompanyInfo("company_info","nmpl_company_info"),

    CompanyHeartbeat("company_heartbeat","nmpl_company_heartbeat"),

    DataCollect("data_collect","nmpl_data_collect"),

    SystemHeart("system_heartbeat","nmpl_system_heartbeat"),

    TerminalUser("terminal_user","nmpl_terminal_user"),

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
