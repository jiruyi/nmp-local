package com.matrictime.network.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author by wangqiang
 * @date 2023/8/16.
 */
public enum TerminalUserEnum {

    KEY_MATCH("01","密钥匹配"),

    REGISTER("02","注册"),

    ON_LINE("03","上线"),

    OFF_LINE("04","下线"),

    LOG_OUT("05","注销"),

    ONE_MACHINE("21","一体机"),

    SECURITY_SERVER("22","安全服务器");

    private String code;
    private String conditionDesc;

    public String getCode() {
        return code;
    }

    public String getConditionDesc() {
        return conditionDesc;
    }

    TerminalUserEnum(String code, String conditionDesc) {
        this.code = code;
        this.conditionDesc = conditionDesc;
    }

    public static HashMap<String,TerminalUserEnum> terminalUserEnumMap = new HashMap<String,TerminalUserEnum>(){
        {
            put("01",KEY_MATCH);
            put("02",REGISTER);
            put("03",ON_LINE);
            put("04",OFF_LINE);
            put("05",LOG_OUT);
            put("21",ONE_MACHINE);
            put("22",SECURITY_SERVER);
        }

    };

    public static Map<String,TerminalUserEnum> getTerminalUserEnumMap(){
        return terminalUserEnumMap;
    }

}
