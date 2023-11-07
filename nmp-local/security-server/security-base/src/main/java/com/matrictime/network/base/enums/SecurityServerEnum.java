package com.matrictime.network.base.enums;

public enum SecurityServerEnum {
    STATUS_ONLINE("01","上线"),
    STATUS_OFFLINE("02","下线");

    SecurityServerEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 编码
     */
    private String code;

    /**
     * 描述
     */
    private String description;

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
