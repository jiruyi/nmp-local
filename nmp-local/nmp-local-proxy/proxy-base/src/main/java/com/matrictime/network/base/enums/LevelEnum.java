package com.matrictime.network.base.enums;

public enum LevelEnum {
    SERIOUS("1","seriousCount","严重",0),
    EMERG("2","emergentCount","紧急",1),
    SAMEAS("3","sameAsCount","一般",2);
    private String  level;
    private String code;
    private String desc;
    private Integer redisIndex;

    LevelEnum(String level, String code, String desc, Integer redisIndex) {
        this.level = level;
        this.code = code;
        this.desc = desc;
        this.redisIndex = redisIndex;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getRedisIndex() {
        return redisIndex;
    }

    public void setRedisIndex(Integer redisIndex) {
        this.redisIndex = redisIndex;
    }
}
