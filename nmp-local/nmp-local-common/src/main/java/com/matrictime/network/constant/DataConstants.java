package com.matrictime.network.constant;

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

    public static final String EDIT_TYPE_ADD = "1";
    public static final String EDIT_TYPE_UPD = "2";
    public static final String EDIT_TYPE_DEL = "3";

    public static final String EDIT_RANGE_PART = "1";
    public static final String EDIT_RANGE_ALL = "2";

    public static final boolean IS_EXIST = true;
    public static final boolean IS_NOT_EXIST = false;

    public static final String FILE_TYPE_CSV = ".csv";


}
