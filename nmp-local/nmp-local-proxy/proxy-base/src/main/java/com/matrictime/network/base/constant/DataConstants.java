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

    public static final String PASSWORD_ERROR_PREFIX = "_password_error_number";
    public static final String USER_ACCOUNT_LOCK_PREFIX = "_user_account_lock";
    public static final String USER_LOGIN_JWT_TOKEN = "_jwt_token";
    public static final String USER_LOGIN_SMS_CODE= "login_sms_code:";
    public static final String USER_ROUTE_SMS_CODE= "route_sms_code:";

    public static final String PASSWORD_ERROR_INIT_VALUE = "1:N";

    public static final String USER_ACCOUNT_NORMAL = "N";

    public static final String USER_ACCOUNT_LOCK = "L";

    public static final Integer PASSWORD_ERROR_NUM = 3;

    public static final String REQUESET_HEADER_TOKEN = "jwt-token";

    public static final String SUCCESS_MSG = "success";

    public static final String HEALTH_MONITOR_DEVICE_ID = "health_monitor_device_id:";

    public static final String KEY_SPLIT = ":";

    public static final String PHONE_SMS_DAY_COUNT = "phone_sms_count_day:";

    public static final String NETWORK_LOG_COUNT = "network_log_count";

    public static final String DEVICE_SPLIT = ",";
    public static final String STR_SPLIT = ",";
    public static final String FILE_SPLIT = "\\.";
    public static final String SOURCE_LAST_KEY = "sourceLastMap";
    public static final String TARGET_LAST_KEY = "targetLastMap";
    public static final String DEVICE_LOG_REDIS_CHANNEL = "DEVICE_LOG";


    public static final long SUPER_ADMIN = 1;

    public static final long COMMON_ADMIN = 2;

    public static final String IOTYPE_I = "1";
    public static final String IOTYPE_O = "0";

    // 基站
    public static final String DEVICE_BIG_TYPE_0 = "0";
    // 分发机、加密机等
    public static final String DEVICE_BIG_TYPE_1 = "1";

    public static final String SYSTEM_ID_0 = "00";
    public static final String SYSTEM_ID_1 = "01";
    public static final String SYSTEM_ID_2 = "02";
    public static final String SYSTEM_ID_3 = "03";

    public static final String STATION_STATUS_UNACTIVE = "01";
    public static final String STATION_STATUS_ACTIVE = "02";
    public static final String STATION_STATUS_DOWN = "04";

    public static final String CONFIG_DEVICE_TYPE_1 = "1";
    public static final String CONFIG_DEVICE_TYPE_2 = "2";
    public static final String CONFIG_DEVICE_TYPE_3 = "3";
    public static final String CONFIG_DEVICE_TYPE_4 = "4";

    public static final String CONFIGURATION_DEVICE_TYPE_1 = "1";
    public static final String CONFIGURATION_DEVICE_TYPE_2 = "2";
    public static final String CONFIGURATION_DEVICE_TYPE_3 = "3";
    public static final String CONFIGURATION_DEVICE_TYPE_4 = "4";

    public static final Boolean VERSION_FILE_IS_PUSHED = true;

    public static final Boolean VERSION_FILE_IS_STARTED = true;

    public static final Integer LEVEL_1 = 1;
    public static final Integer LEVEL_2 = 2;
    public static final Integer LEVEL_3 = 3;

    // TODO: 2022/6/27 上线前需要确认配置信息
    public static final String INTRANET_BROADBAND_LOAD_CODE = "10004";
    public static final String INTERNET_BROADBAND_LOAD_CODE = "10005";
    public static final String TIMER_SHAFT = "timerShaft";

    public static final String CONFIGURATION_TYPE_CONFIG = "1";
    public static final String CONFIGURATION_TYPE_SIGNAL = "2";
    public static final String CONFIGURATION_TYPE_PUSHFILE = "3";
    public static final String CONFIGURATION_TYPE_STARTFILE = "4";
    public static final String CONFIGURATION_TYPE_ROUTER = "5";
    public static final String CONFIGURATION_TYPE_LINK = "6";

    /**
     * redis key
     */
    public static final String HEART_CHECK_DEVICE_ID = "heart_check_device_id:";

    /**
     * map key
     */
    public static final String KEY_DEVICE_ID = "deviceId";
    public static final String KEY_FILE_ID = "fileId";
    public static final String KEY_CONFIG_CODE = "configCode";
    public static final String KEY_CONFIG_VALUE = "configValue";
    public static final String KEY_UNIT = "unit";
    public static final String KEY_SUCCESS_IDS = "successIds";
    public static final String KEY_FAIL_IDS = "failIds";
    public static final String KEY_IO_TYPE = "opType";
    public static final String KEY_IS_SUCCESS = "isSuccess";
    public static final String KEY_URL = "url";
    public static final String KEY_USER_ID = "userId";
    public static final String KEY_FILE_PATH = "filePath";
    public static final String KEY_FILE_NAME = "fileName";

    /**
     * table name
     */
    public static final String NMPL_BASE_STATION_INFO = "nmpl_base_station_info";

    public static final String NMPL_LOCAL_BASE_STATION_INFO = "nmpl_local_base_station_info";

    public static final String NMPL_DEVICE_INFO = "nmpl_device_info";

    public static final String NMPL_LOCAL_DEVICE_INFO = "nmpl_local_device_info";

    public static final String NMPL_ROUTE = "nmpl_route";

    public static final String NMPL_BUSINESS_ROUTE = "nmpl_business_route";

    public static final String NMPL_INTERNET_ROUTE = "nmpl_internet_route";

    public static final String NMPL_STATIC_ROUTE = "nmpl_static_route";

    public static final String NMPL_LINK = "nmpl_link";

    public static final String NMPL_OUTLINE_PC_INFO = "nmpl_outline_pc_info";

    public static final String NMPL_CONFIG = "nmpl_config";


    /**
     * netmanage url
     */

    public static final String HEART_REPORT_URL = "/nmp-local-business/monitor/checkHeart";

    public static final String INIT_URL = "/nmp-local-business/proxy/init";

    public static final String LOG_PUSH_URL = "/nmp-local-business/log/device/save";

    public static final String PC_DATA_URL = "/nmp-local-business/baseStation/savePcData";

    public static final String DATA_COLLECT_URL="/nmp-local-business/systemDataCollect/insertSystemData";


    public static final String BILL_URL = "/nmp-local-business/bill/saveBill";

    public static final String PHYSICAL_DEVICE_HEARTBEAT_URL = "/nmp-local-business/monitor/devHeartbeat";

    public static final String PHYSICAL_DEVICE_RESOURCE_URL = "/nmp-local-business/monitor/devResource";

    public static final String SYSTEM_RESOURCE_URL = "/nmp-local-business/monitor/sysResource";

    public static final String SYSTEM_HEARTBEAT_URL = "/nmp-local-business/systemHeartbeat/updateSystemHeartbeat";

    public static final String TERMINAL_USER_URL = "/nmp-local-business/terminalUser/updateTerminalUser";

    public static final String TERMINAL_DATA_URL = "/nmp-local-business/terminalData/collectTerminalData";

    public static final String UPDATE_CURRENT_URL = "/nmp-local-business/stationConnectCount/updateStationConnectCount";

    public static final Integer ERROR_MSG_MAXLENGTH = 250;

    public static final Integer HEART_REPORT_SPACE = 30;

    public static final long FILE_SIZE = 1024*1024*100;

    public static final String OPER_INSTALL = "install.sh";
    public static final String OPER_UPDATE = "update.sh";
    public static final String OPER_RUN = "run.sh";
    public static final String OPER_STOP = "stop.sh";
    public static final String OPER_UNINSTALL = "uninstall.sh";

    public static final String OPER_TARGZ = "targz.sh";


}
