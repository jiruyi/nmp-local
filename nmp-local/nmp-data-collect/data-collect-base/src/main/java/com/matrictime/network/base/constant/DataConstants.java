package com.matrictime.network.base.constant;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project microservicecloud-jzsg
 * @date 2021/9/6 0006 16:09
 * @desc
 */
public class DataConstants {

    public  static  final  int ZERO = 0;

    public static final String KEY_SPLIT = ":";

    public static final String INSERT_DATA_COLLECT = "/nmp-center-business/dataCollect/insertDataCollect";

    public static final String RECEIVE_TERMINAL_USER = "/nmp-center-business/companyTerminalUser/receiveTerminalUser";

    public static final String INSERT_COMPANY_HEARTBEAT = "/nmp-center-business/companyHeartbeat/insertCompanyHeartbeat";

    public static final String RECEIVE_STATION_SUMMARY = "/nmp-center-business/companyStationSummary/receiveStationSummary";

    public static final String RECEIVE_COMPANY = "/nmp-center-business/company/receiveCompany";

    public static final double BASE_NUMBER = 1024.0;

    public static final int HALF_HOUR_SECONDS = 1800;

    public static final int BYTE_TO_BPS = 8;

    public static final int RESERVE_DIGITS  = 2;

    public static final String NMPL_ALARM_INFO  = "nmpl_alarm_info";

    public static final String NMPL_COMPANY_HEARTBEAT  = "nmpl_company_heartbeat";

    public static final String NMPL_COMPANY_INFO = "nmpl_company_info";

    public static final Long ALARM_INFO_EVERY_COUNT  = 200l;

    public static final String SPLIT_LINE  = "-";

}
