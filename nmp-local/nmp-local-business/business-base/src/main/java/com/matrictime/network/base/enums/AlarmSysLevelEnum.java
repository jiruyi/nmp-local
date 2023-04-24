package com.matrictime.network.base.enums;

import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.model.AlarmInfo;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/4/23 0023 16:01
 * @desc
 */
public enum AlarmSysLevelEnum {

    ACCESS("01","接入基站",0,"1","access"),
    BOUNDARY("02","边界基站",1,"2","boundary"),
    KEYCENTER("11","密钥中心",2,"3","keyCenter");
    private String  type;
    private String Desc;
    private Integer redisIndex;
    private String  level;
    private String  code;

    AlarmSysLevelEnum(String type, String desc, Integer redisIndex, String level, String code) {
        this.type = type;
        Desc = desc;
        this.redisIndex = redisIndex;
        this.level = level;
        this.code = code;
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

    public Integer getRedisIndex() {
        return redisIndex;
    }

    public void setRedisIndex(Integer redisIndex) {
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

    /**
      * @title getCodeCount
      * @param [dateList, redisValue]
      * @return java.util.Map<java.lang.String,java.lang.Long>
      * @description
      * @author jiruyi
      * @create 2023/4/23 0023 16:27
      */
    public static Map<String, Long> getCodeCount(List<AlarmInfo> dateList, String redisValue){
        Map<String, Long> countMap =  new ConcurrentHashMap<>();
        for(AlarmSysLevelEnum conTypeEnum : AlarmSysLevelEnum.values()){
            //获取条数
            long count = dateList.stream().
                    filter(alarmInfo -> conTypeEnum.getLevel().equals(alarmInfo.getAlarmLevel())).count();
            //判断redis是否有值
            if(!StringUtils.isEmpty(redisValue)){
                //redis原有值 累加
                List<String> countList = Arrays.asList(redisValue.split(DataConstants.VLINE));
                count+=Long.valueOf(countList.get(conTypeEnum.redisIndex));
            }
            countMap.put(conTypeEnum.getCode(),count);
        }
        return countMap;
    }
}
