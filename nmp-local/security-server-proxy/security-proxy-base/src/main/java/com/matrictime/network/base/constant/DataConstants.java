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

    public static final String USER_ACCOUNT_NORMAL = "N";

    public static final String USER_ACCOUNT_LOCK = "L";

    public static final Integer PASSWORD_ERROR_NUM = 3;

    public static final String REQUESET_HEADER_TOKEN = "jwt-token";

    public static final String SUCCESS_MSG = "success";

    public static final String NETWORK_LOG_COUNT = "network_log_count";

    public static final String DEVICE_SPLIT = ",";
    public static final String STR_SPLIT = ",";
    public static final String FILE_SPLIT = "\\.";
    public static final String SOURCE_LAST_KEY = "sourceLastMap";
    public static final String TARGET_LAST_KEY = "targetLastMap";
    public static final String DEVICE_LOG_REDIS_CHANNEL = "DEVICE_LOG";


    public static final long SUPER_ADMIN = 1;

    public static final long COMMON_ADMIN = 2;

    /**
     * redis key
     */
    public static final String HEART_REPORT_NETWORK_ID = "HEART_REPORT_NETWORK_ID:";

    /**
     * map key
     */
    public static final String KEY_CONFIG_CODE = "configCode";
    public static final String KEY_CONFIG_VALUE = "configValue";
    public static final String KEY_UNIT = "unit";
    public static final String KEY_SUCCESS_IDS = "successIds";
    public static final String KEY_FAIL_IDS = "failIds";
    public static final String KEY_IS_SUCCESS = "isSuccess";
    public static final String KEY_URL = "url";
    public static final String KEY_USER_ID = "userId";
    public static final String KEY_HEART_INFO_VOS = "heartInfoVos";

    public static final String KEY_COM_IP = "comIp";

    /**
     * table name
     */
    public static final String NMPL_BASE_STATION_INFO = "nmpl_base_station_info";


    /**
     * security_server url
     */

    public static final String HEART_REPORT_URL = "/server/heartReport";
    public static final String HEART_INIT_URL = "/common/init";

    public static final Integer ERROR_MSG_MAXLENGTH = 250;

    public static final int HEART_REPORT_SPACE = 30;

    public static final long FILE_SIZE = 1024*1024*100;

    public static final String OPER_INSTALL = "install.sh";
    public static final String OPER_UPDATE = "update.sh";
    public static final String OPER_RUN = "run.sh";
    public static final String OPER_STOP = "stop.sh";
    public static final String OPER_UNINSTALL = "uninstall.sh";

    public static final String OPER_TARGZ = "targz.sh";


}
