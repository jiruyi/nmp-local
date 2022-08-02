package com.matrictime.network.base.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project microservicecloud-jzsg
 * @date 2021/9/15 0015 16:43
 * @desc
 */
public enum DeviceLevelEnum {
    THREELEVEL("3","threeLevel","三级基站"),
    FOURLEVEL("4","fourLevel","四级基站"),
    FIVELEVEL("5","fiveLevel","五级基站"),
    SIXLEVEL("6","sixLevel","一体机");
    private String code;
    private String mapKey;
    private String conditionDesc;

    public String getCode() {
        return code;
    }

    public String getMapKey() {
        return mapKey;
    }

    public void setMapKey(String mapKey) {
        this.mapKey = mapKey;
    }

    public String getConditionDesc() {
        return conditionDesc;
    }

    DeviceLevelEnum(String code, String conditionDesc) {
        this.code = code;
        this.conditionDesc = conditionDesc;
    }
    DeviceLevelEnum(String code,String mapKey, String conditionDesc) {
        this.code = code;
        this.mapKey = mapKey;
        this.conditionDesc = conditionDesc;
    }
    public static  DeviceLevelEnum getEnumByCode(String code){
        for(DeviceLevelEnum deviceLevelEnum : DeviceLevelEnum.values()){
            if (Objects.equals(deviceLevelEnum.getCode(), code)) {
                return deviceLevelEnum;
            }
        }
        return null;
    }
    public static  String getMapKeyByCode(int code){
        for(DeviceLevelEnum deviceLevelEnum : DeviceLevelEnum.values()){
            if (Objects.equals(deviceLevelEnum.getCode(), String.valueOf(code))) {
                return deviceLevelEnum.mapKey;
            }
        }
        return null;
    }
    public static  String getLevelByMapkey(String mapKey){
        for(DeviceLevelEnum deviceLevelEnum : DeviceLevelEnum.values()){
            if (Objects.equals(deviceLevelEnum.getMapKey(), String.valueOf(mapKey))) {
                return deviceLevelEnum.code;
            }
        }
        return null;
    }
    public static Map<String,String> getMap(){
        Map<String,String> map = new HashMap<>();
        for(DeviceLevelEnum deviceLevelEnum : DeviceLevelEnum.values()){
            map.put(deviceLevelEnum.mapKey,"");
        }
        return map;
    }
}
