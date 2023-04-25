package com.matrictime.network.base.enums;

import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.model.AlarmInfo;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/4/21 0021 14:25
 * @desc
 */
public enum AlarmPhyConTypeEnum {
    CPU("1","cpu过高",0,"cpu"),
    MEM("2","内存不足",1,"mem"),
    DISK("3","磁盘不足",2,"disk"),
    FLOW("4","流量过载",3,"flow");
    private String contentType;
    private String  Desc;
    private Integer redisIndex;
    private String  code;

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

    public List<String> getContentTypeList(){
        List<String> typeList = new ArrayList<>();
        for(AlarmPhyConTypeEnum alarmConTypeEnum : AlarmPhyConTypeEnum.values()){
            typeList.add(alarmConTypeEnum.getContentType());
        }
        return typeList;
    }

    /**
     * @title getCodeCount
     * @param
     * @return java.util.Map<java.lang.String,java.lang.Long>
     * @description  分组 count
     * @author jiruyi
     * @create 2023/4/23 0023 11:12
     */
    public static Map<String, Long>   getCodeCount( List<AlarmInfo> dateList, String redisValue){
        Map<String, Long> countMap =  new ConcurrentHashMap<>();
        for(AlarmPhyConTypeEnum conTypeEnum : AlarmPhyConTypeEnum.values()){
            //获取条数
            long count = dateList.stream().
                    filter(alarmInfo -> conTypeEnum.getContentType().equals(alarmInfo.getAlarmContentType())).count();
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
