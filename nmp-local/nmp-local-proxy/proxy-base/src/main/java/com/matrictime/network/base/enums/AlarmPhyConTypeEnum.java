package com.matrictime.network.base.enums;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/4/21 0021 14:25
 * @desc
 */
public enum AlarmPhyConTypeEnum {
    CPU("1", "cpu过高", 0, "cpu"),
    MEM("2", "内存不足", 1, "mem"),
    DISK("3", "磁盘不足", 2, "disk"),
    FLOW("4", "流量过载", 3, "flow");
    private String contentType;
    private String Desc;
    private Integer redisIndex;
    private String code;

    AlarmPhyConTypeEnum(String contentType, String desc, Integer redisIndex, String code) {
        this.contentType = contentType;
        Desc = desc;
        this.redisIndex = redisIndex;
        this.code = code;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public Integer getRedisIndex() {
        return redisIndex;
    }

    public void setRedisIndex(Integer redisIndex) {
        this.redisIndex = redisIndex;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
