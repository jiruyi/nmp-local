package com.matrictime.network.enums;

public enum AlarmSysLevelEnum {


    ACCESS("01","接入基站","access","station_id","nmpl_base_station_info"),
    BOUNDARY("02","边界基站","boundary","station_id","nmpl_base_station_info"),
    KEYCENTER("11","密钥中心","keyCenter","device_id","nmpl_device_info");
    private String  type;
    private String Desc;
    private String  code;
    private String  columnName;
    private String  tableName;


    AlarmSysLevelEnum(String type, String desc, String code, String columnName, String tableName) {
        this.type = type;
        Desc = desc;
        this.code = code;
        this.columnName = columnName;
        this.tableName = tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

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
        //根据level获取对用code 作为map key
        public static String getCodeBylevel(String level){
            for(LevelEnum levelEnum : LevelEnum.values()){
                if(levelEnum.getLevel().equals(level)){
                    return levelEnum.getCode();
                }
            }
            return null;
        }
    }
}
