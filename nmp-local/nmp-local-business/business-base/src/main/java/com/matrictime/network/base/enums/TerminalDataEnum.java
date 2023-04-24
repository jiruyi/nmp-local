package com.matrictime.network.base.enums;

/**
 * @author by wangqiang
 * @date 2023/4/23.
 */
public enum TerminalDataEnum {

    RESIDUE("01","剩余"),

    SUPPLEMENT("02","补充"),

    USE("03","使用");

    private String code;

    private String conditionDesc;

    public String getCode() {
        return code;
    }

    public String getConditionDesc() {
        return conditionDesc;
    }

    TerminalDataEnum(String code, String conditionDesc){
        this.code = code;
        this.conditionDesc = conditionDesc;
    }
}
