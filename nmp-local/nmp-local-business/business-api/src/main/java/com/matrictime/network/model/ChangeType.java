package com.matrictime.network.model;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2024/4/24 0024 16:46
 * @desc
 */
public enum ChangeType {

    ADD("1","数据增加"),
    DELETE("2","数据删除"),
    UPDATE("3","数据修改");
    String code;
    String name;
    ChangeType(String code,String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
