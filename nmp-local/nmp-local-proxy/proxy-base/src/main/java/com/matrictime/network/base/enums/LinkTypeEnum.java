package com.matrictime.network.base.enums;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project microservicecloud-jzsg
 * @date 2021/9/15 0015 15:22
 * @desc
 */
public enum LinkTypeEnum {
    BS("01","边界基站-边界基站"),
    BK("02","基站-秘钥中心"),
    BC("03","基站-缓存机"),
    KG("04","秘钥中心-生成机");

    private String code;
    private String conditionDesc;
    public String getCode() {
        return code;
    }

    public String getConditionDesc() {
        return conditionDesc;
    }

    LinkTypeEnum(String code, String conditionDesc) {
        this.code = code;
        this.conditionDesc = conditionDesc;
    }
}
