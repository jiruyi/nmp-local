package com.matrictime.network.base.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public static final String SECURITY_SERVER= "security_server_";
    public static final Integer FILE_SIZE = 1024 * 1024 * 10;

    public static final String IMG_TYPE_PNG = "png";

    public static final String LAST_UP_DATA_VALUE = "1000";
    public static final String USED_UP_DATA_VALUE = "1001";
    public static final String LAST_DOWN_DATA_VALUE = "2000";
    public static final String USED_DOWN_DATA_VALUE = "2001";

    public static final Map<Integer,String> SERVER_STATUS = new HashMap<Integer, String>(){
        {
            put(1,"在线");
            put(2,"离线");
        }
    };

    public static final List<String> KEY_DATA_TIME = new ArrayList<String>(){
        {
            add("00:30");
            add("01:00");add("01:30");
            add("02:00");add("02:30");
            add("03:00");add("03:30");
            add("04:00");add("04:30");
            add("05:00");add("05:30");
            add("06:00");add("06:30");
            add("07:00");add("07:30");
            add("08:00");add("08:30");
            add("09:00");add("09:30");
            add("10:00");add("10:30");
            add("11:00");add("11:30");
            add("12:00");add("12:30");
            add("13:00");add("13:30");
            add("14:00");add("14:30");
            add("15:00");add("15:30");
            add("16:00");add("16:30");
            add("17:00");add("17:30");
            add("18:00");add("18:30");
            add("19:00");add("19:30");
            add("20:00");add("20:30");
            add("21:00");add("21:30");
            add("22:00");add("22:30");
            add("23:00");add("23:30");
            add("24:00");
        }
    };

    public static final Integer KEY_INFO_DISPLAY_GAP = 30;

    public static final String END_DATE_TIME = "END_DATE_TIME";

    public static final String VALUE_LIST = "VALUE_LIST";

    public static final Short OPERATE_TYPE_START_SERVER = 1000;

    public static final Short OPERATE_TYPE_UPDATE_KEY = 1001;
    public static final Short OPERATE_STATUS_INIT = 9999;
    public static final Short OPERATE_STATUS_WAIT = 1000;
    public static final Short OPERATE_STATUS_FINISH = 1001;

    public static final int ONE_THOUSAND_AND_TWENTY_FOUR = 1024;

    public static final double ZERO_DOUBLE = 0.0;

    /**
     * redis key
     */
    public static final String HEART_REPORT_NETWORKID = "HEART_REPORT_NETWORKID:";

    /**
     * url
     */
    public static final String SERVER_UPDATE_URL = "/server/update";

    /**
     * map key
     */
    public static final String JSON_KEY_EDITTYPE = "editType";
    public static final String JSON_KEY_SECURITYSERVERINFOVOS = "securityServerInfoVos";

}
