package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.constant.RedisKeyConstants;
import com.matrictime.network.base.enums.AlarmConTypeEnum;
import com.matrictime.network.base.util.DateUtils;
import com.matrictime.network.dao.domain.AlarmDataDomainService;
import com.matrictime.network.dao.mapper.NmplAlarmInfoMapper;
import com.matrictime.network.dao.mapper.extend.NmplAlarmInfoExtMapper;
import com.matrictime.network.model.AlarmInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/4/19 0019 15:50
 * @desc  警告信息数据处理
 */
@Service
@Slf4j
public class AlarmDataDomainServiceImpl extends SystemBaseService implements AlarmDataDomainService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private NmplAlarmInfoMapper alarmInfoMapper;

    @Autowired
    private NmplAlarmInfoExtMapper alarmInfoExtMapper;
    /**
     * @title acceptAlarmData
     * @param [alarmInfoList]
     * @return com.matrictime.network.model.Result
     * @description  数据推送入库
     * @author jiruyi
     * @create 2023/4/19 0019 15:51
     */
    @Override
    public int acceptAlarmData(List<AlarmInfo> alarmInfoList) {
        if(CollectionUtils.isEmpty(alarmInfoList)){
            return NumberUtils.INTEGER_ZERO;
        }
        return  alarmInfoExtMapper.batchInsert(alarmInfoList);
    }

    /**
     * @title alarmCountDataForRedis
     * @param []
     * @return void
     * @description  展示当天（从0点开始计时），近3天，近7天，近1个月，近3个月，近6个月的数据
     *               系统告警：
     *
     * 业务系统告警类型数量统计，包括严重、紧急、一般三种类型，统计占比，展示告警次数
     *
     * 资源告警：以物理设备（IP）为单位，告警类型为CPU过高，内存不足，磁盘不足、流量过载，统计占比，展示告警次数
     *
     * @author jiruyi
     * @create 2023/4/21 0021 10:15
     */
    @Override
    public void alarmCountDataForRedis(List<AlarmInfo> alarmInfoList) {
        if(CollectionUtils.isEmpty(alarmInfoList)){
            return;
        }
        //按照ip分组
        Map<String, List<AlarmInfo>>  alarmInfoMap = alarmInfoList.stream().collect(Collectors.groupingBy(AlarmInfo::getAlarmSourceIp));
        //处理每个ip数据 redis
        alarmInfoMap.keySet().stream().forEach(ip ->{
            // {"2023-04-13":1-4-2-3,"202304-14":1-4-2-3,"2023-04-15":1-4-2-3},
            Map<Date,String> physicalValueMap = null;
            //查询redis
            if(!redisTemplate.hasKey(ip+RedisKeyConstants.PHYSICAL_ALARM_COUNT)){
                physicalValueMap = new ConcurrentHashMap<>();
            }else {
                physicalValueMap = redisTemplate.opsForHash().entries(ip+RedisKeyConstants.PHYSICAL_ALARM_COUNT);
            }
            //按照告警时间分组
            Map<Date, List<AlarmInfo>>  dateListMap =
                    alarmInfoMap.get(ip).stream().collect(Collectors.groupingBy(alarmInfo -> {
                        return DateUtils.dateToDate(alarmInfo.getAlarmUploadTime());
                    }));
            //统计本次这个时间的条数
            for(Date date : dateListMap.keySet()){
                List<AlarmInfo> dateList = dateListMap.get(date);
                long cpuCount = dateList.stream().filter(alarmInfo -> AlarmConTypeEnum.CPU.getCode() == alarmInfo.getAlarmContentType()).count();
                long memCount = dateList.stream().filter(alarmInfo -> AlarmConTypeEnum.MEM.getCode() == alarmInfo.getAlarmContentType()).count();
                long diskCount = dateList.stream().filter(alarmInfo -> AlarmConTypeEnum.DISK.getCode() == alarmInfo.getAlarmContentType()).count();
                long flowCount = dateList.stream().filter(alarmInfo -> AlarmConTypeEnum.FLOW.getCode() == alarmInfo.getAlarmContentType()).count();
                //判断redis是否有值
                if(!StringUtils.isEmpty(physicalValueMap.get(date))){
                    //redis原有值 累加
                    List<String> countList = Arrays.asList(physicalValueMap.get(date).split("-"));
                    cpuCount+=Long.valueOf(countList.get(0));
                    memCount+=Long.valueOf(countList.get(1));
                    diskCount+=Long.valueOf(countList.get(2));
                    flowCount+=Long.valueOf(countList.get(3));
                }
                //放入每个时间的最新累计值
                physicalValueMap.put(DateUtils.dateToDate(date),cpuCount+"-"+memCount+"-"+diskCount+"-"+flowCount);
            }
            //放入redis
            redisTemplate.opsForHash().putAll(ip+RedisKeyConstants.PHYSICAL_ALARM_COUNT,physicalValueMap);
        });
    }
}
