package com.matrictime.network.base;

public class UcConstants {

    public static final String DESTINATION_OUT = "0";
    public static final String DESTINATION_IN = "1";
    public static final String DESTINATION_OUT_TO_IN = "2";
    public static final String DESTINATION_OUT_TO_IN_SYN = "3";
    public static final String DESTINATION_FOR_DES = "4";
    public static final String DESTINATION_DEFAULT = "5";
    public static final String DESTINATION_FOR_ENC = "6";

    public static final String URL_LOGIN = "/user/login";

    public static final String URL_LOGOUT = "/user/logout";

    public static final String URL_SYSLOGOUT = "/user/syslogout";

    public static final String URL_REGISTER = "/user/register";

    public static final String URL_DELETEUSER = "/user/deleteUser";

    public static final String URL_BIND = "/user/bind";

    public static final String URL_VERIFY = "/verify";

    public static final String URL_CANCEL_USER = "/userFriends/cancelUser";

    public static final String URL_SELECT_USER_FRIEND = "/userFriends/selectUserFriend";

    public static final String URL_ADD_FRIENDS = "/userFriends/addFriends";

    public static final String URL_CREATEGROUP="/group/createGroup";

    public static final String URL_DELETEGROUP="/group/deleteGroup";

    public static final String URL_MODIFYGROUP="/group/modifyGroup";

    public static final String URL_QUERYGROUP="/group/queryGroup";

    public static final String URL_CREATEUSERGROUP="/group/createUserGroup";

    public static final String URL_DELETEUSERGROUP="/group/deleteUserGroup";

    public static final String URL_MODIFYUSERGROUP="/group/modifyUserGroup";

    public static final String URL_CHANGEPASSWD="/changePasswd";

    public static final String URL_QUERYUSERINFO="/queryUserInfo";

    public static final String URL_GETADDUSERINFO="/userFriends/getAddUserInfo";

    public static final String URL_MODIFYUSERINFO="/modifyUserInfo";

    public static final String URL_DELETEFRIEND="/deleteFriend";

    public static final String LOGOUT_MSG = "logout";

    /** rediskey begin**/
    public static final String REDIS_LOGIN_KEY = "redisson:uc:account:";
    /** rediskey end**/
}
