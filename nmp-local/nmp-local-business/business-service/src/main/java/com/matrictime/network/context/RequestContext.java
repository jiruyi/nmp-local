package com.matrictime.network.context;

import com.matrictime.network.base.enums.SystemUserEnum;
import com.matrictime.network.dao.model.NmplUser;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;


/**
 * @title
 * @param
 * @return
 * @description
 * @author jiruyi
 * @create 2021/9/7 0007 14:29
 */
public class RequestContext {

    private static final String THREADLOCAL_KEY_USER = "user";

    private static final String THREADLOCAL_KEY_REQUEST = "request";

    private static final String REQUEST_START_TIMESTAMP = "request_start_timestamp";

    private static final ThreadLocal<Map<String, Object>> local = new ThreadLocal<>();

    public static void setUser(NmplUser user) {
        local.get().put(THREADLOCAL_KEY_USER, user);
    }
    public static void setUserInfo(SystemUserEnum systemUserEnum) {
        NmplUser user = NmplUser.builder().
                loginAccount(systemUserEnum.getLoginAccount()).
                nickName(systemUserEnum.getNickName()).build();
        local.get().put(THREADLOCAL_KEY_USER, user);
    }
    public static NmplUser getUser() {
        return (NmplUser) local.get().get(THREADLOCAL_KEY_USER);
    }

    public static void setRequest(HttpServletRequest request) {
        local.get().put(THREADLOCAL_KEY_REQUEST, request);
    }

    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) local.get().get(THREADLOCAL_KEY_REQUEST);
    }

    public static void setRequestStartTimestamp() {
        init();
        local.get().put(REQUEST_START_TIMESTAMP, Instant.now().toEpochMilli());
    }

    public static Long getRequestStartTimestamp(){
        return (Long) local.get().get(REQUEST_START_TIMESTAMP);
    }

    public static void clean() {
        local.remove();
    }

    public static void remove(String key) {
        if (local.get() != null) {
            local.get().remove(key);
        }
    }
    private static void init() {
        if (local.get() == null) {
            local.set(new HashMap<>());
        }
    }

}
