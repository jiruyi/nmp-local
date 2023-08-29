package com.matrictime.network.base.enums;

public enum BusinessTypeEnum {
    ;
    /**
     * 业务类型
     */
    private String code;
    /**
     * 业务描述
     */
    private String description;


    BusinessTypeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
