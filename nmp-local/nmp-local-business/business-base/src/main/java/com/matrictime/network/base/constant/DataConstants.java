package com.matrictime.network.base.constant;

import java.util.HashMap;
import java.util.Map;

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

    public static final Integer FILE_SIZE = 1024 * 1024 * 100;

    public static int VERSION_DES_MAX_LENGTH = 1000;

    public static final Integer FILE_IS_EXIT = 4;

    public static final Integer FILE_UPDATE_SUCCESS = 5;

    public static final Double EMPTY_FLOW = 0.0;

    public static final String LINUX_SEPARATOR = "/";

    public static final String USER_LOGIN_STATUS = "_login_status";

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
    public static final String SYSTEM_ID_1 = "11";
    public static final String SYSTEM_ID_2 = "12";
    public static final String SYSTEM_ID_3 = "13";

    public static final String STATION_STATUS_UNACTIVE = "01";
    public static final String STATION_STATUS_ACTIVE = "02";
    public static final String STATION_STATUS_DOWN = "04";
    public static final String STATION_STATUS_ABNORMAL = "03";

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

    public static final String SYSTEM_RESOURCE = "system_resource";

    /**
     * map key
     */
    public static final String KEY_DEVICE_ID = "deviceId";
    public static final String KEY_FILE_ID = "fileId";
    public static final String KEY_CONFIG_CODE = "configCode";
    public static final String KEY_CONFIG_VALUE = "configValue";
    public static final String KEY_UNIT = "unit";
    public static final String KEY_EDIT_TYPE = "editType";
    public static final String KEY_DEVICE_TYPE = "deviceType";
    public static final String KEY_CONFIGVOS = "configVos";

    public static final String KEY_REPORT_BUSINESS = "reportBusiness";
    public static final String KEY_SUCCESS_IDS = "successIds";
    public static final String KEY_FAIL_IDS = "failIds";

    public static final String KEY_CONFIG_IDS = "configIds";
    public static final String KEY_IO_TYPE = "opType";
    public static final String KEY_IS_SUCCESS = "isSuccess";
    public static final String KEY_URL = "url";
    public static final String KEY_USER_ID = "userId";
    public static final String KEY_FILE_PATH = "filePath";
    public static final String KEY_FILE_NAME = "fileName";
    public static final String KEY_DATA = "data";




    public static final String KEY_SUCCESS ="success";
    public static final String URL_STATION_INSERT="/baseStation/insert";
    public static final String URL_STATION_UPDATE="/baseStation/update";
    public static final String URL_DEVICE_INSERT="/device/insert";
    public static final String URL_DEVICE_UPDATE="/device/update";



    public static final String URL_ROUTE_INSERT="/route/insert";

    public static final String URL_ROUTE_UPDATE="/route/update";

    public static final String URL_ROUTE_DELETE="/route/delete";

    public static final String URL_LINK_RELATION_INSERT="/linkRelation/insert";

    public static final String URL_LINK_RELATION_UPDATE="/linkRelation/update";

    public static final String URL_LINK_RELATION_DELETE="/linkRelation/delete";

    public static final String URL_OUTLINEPC_INSERTORUPDATE = "/outlinePc/updateOutlinePc";


    public static final String URL_OUTLINEPC_BATCHINSERT = "/outlinePc/batchInsertOutlinePc";


    public static final Integer INSERT_OR_UPDATE_SUCCESS = 1;

    public static final String UPDATE_STATIC_ROUTE = "/route/updateStaticRoute";

    public static final String INSERT_STATIC_ROUTE = "/route/addStaticRoute";

    public static final String UPDATE_INTERNET_ROUTE = "/route/updateInternetRoute";

    public static final String INSERT_INTERNET_ROUTE = "/route/addInternetRoute";

    public static final String UPDATE_BUSINESS_ROUTE = "/route/updateBusinessRoute";


    public static final String OPERATOR_TYPE = "00";
    public static final String REGION_TYPE = "01";
    public static final String VILLAGE_TYPE = "02";

    public static final double BYTE_TO_KB = 128.0;

    public static final Map<String, String> DATA_COLLECT_CONST = new HashMap<String, String>(){
        {
            put("10000","人");
            put("10001","MB");
            put("10002","byte");
            put("10003","MB");
            put("10004","bps");
            put("10005","bps");
            put("10006","bps");
            put("10007","byte");
        }
    };

    public static final double BASE_NUMBER = 1024.0;

    public static final double MAX_SIZE = 999.9;

    public static final int TEN=10;

    public static final String LOAD_FILE = "/version/load";
    public static final String LOAD_RUN_FILE = "/version/start";
    public static final String RUN_FILE = "/version/run";
    public static final String STOP_FILE = "/version/stop";
    public static final String UNINSTALL_FILE = "/version/uninstall";


    public static final String VERSION_INIT_STATUS = "1";
    public static final String VERSION_RUN_STATUS = "2";
    public static final String VERSION_STOP_STATUS = "3";

    public static final String SYSTEM_QIBS = "QIBS";
    public static final String SYSTEM_QEBS = "QEBS";
    public static final String SYSTEM_QKC = "QKC";



    public static final String FLOW_TRANSFOR = "transfor_flow_";

    public static final String BASE_STATION_FLOW_COUNT = "base_station_flow_count";

    public static final String BORDER_BASE_STATION_FLOW_COUNT = "border_base_station_flow_count";

    public static final String KEY_CENTER_FLOW_COUNT = "Key_center_flow_count";

    public static final String CURRENT_FLOW ="current_flow_";

    public static final int BYTE_TO_BPS = 8;

    public static final int HALF_HOUR_SECONDS = 1800;

    public static final int RESERVE_DIGITS  = 2;

    public static final int TWENTY_FOUR = 24;

    public static final int TWELVE = 12;

    public static final int THIRTY = 30;

    public static final String UNDERLINE ="_";

    public static final String VLINE  ="-";

    public static final String DEFAULT_ZERO = "0.00";

    public static final double DOUBLE_ZERO = 0.0;

    public static final String DEFAULT_STR_ZERO = "0";

    public static final String EDIT_CONFIG_URL = "/config/editConfig";

    public static final String SYNC_CONFIG_URL = "/config/syncConfig";

    public static final String DATA_SYNC_CONFIG_URL = "/task/syncConfig";


    public static final String HTTP_TITLE = "http://";

    public static final String LOCAL_IP = "127.0.0.1";

    public static final String COLLEECT_REPORT_URL = "8012/nmp-data-collect/task/report";


}
