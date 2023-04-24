package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.base.enums.AlarmPhyConTypeEnum;
import com.matrictime.network.base.enums.AlarmSysLevelEnum;
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

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.matrictime.network.base.constant.RedisKeyConstants.PHYSICAL_ALARM_COUNT;
import static com.matrictime.network.base.constant.RedisKeyConstants.SYSTEM_ALARM_COUNT;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/4/19 0019 15:50
 * @desc 警告信息数据处理
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

    @Autowired
    private Executor taskExecutor;

    /**
     * @param [alarmInfoList]
     * @return com.matrictime.network.model.Result
     * @title acceptAlarmData
     * @description 数据推送入库
     * @author jiruyi
     * @create 2023/4/19 0019 15:51
     */
    @Override
    public int acceptAlarmData(List<AlarmInfo> alarmInfoList) {
        if (CollectionUtils.isEmpty(alarmInfoList)) {
            return NumberUtils.INTEGER_ZERO;
        }
        //mysql 插入
        int batchCount =  alarmInfoExtMapper.batchInsert(alarmInfoList);
        //redis 插入
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                //redis  物理资源插入
                List<AlarmInfo> phyList = alarmInfoList.stream().filter(Objects::nonNull)
                        .filter(alarmInfo -> "00".equals(alarmInfo.getAlarmSourceType()))
                        .collect(Collectors.toList());
                if(!CollectionUtils.isEmpty(phyList)){
                    alarmPhyCountDataForRedis(phyList);
                }
                //redis  系统资源插入
                List<AlarmInfo> sysList = alarmInfoList.stream().filter(Objects::nonNull)
                        .filter(alarmInfo -> !"00".equals(alarmInfo.getAlarmSourceType()))
                        .collect(Collectors.toList());
                if(!CollectionUtils.isEmpty(sysList)){
                    alarmSysCountDataForRedis(sysList);
                }
            }
        });
        //redis  物理资源插入
//        List<AlarmInfo> phyList = alarmInfoList.stream().filter(Objects::nonNull)
//                .filter(alarmInfo -> "00".equals(alarmInfo.getAlarmSourceType()))
//                .collect(Collectors.toList());
//        if(!CollectionUtils.isEmpty(phyList)){
//            alarmPhyCountDataForRedis(phyList);
//        }
//        //redis  系统资源插入
//        List<AlarmInfo> sysList = alarmInfoList.stream().filter(Objects::nonNull)
//                .filter(alarmInfo -> !"00".equals(alarmInfo.getAlarmSourceType()))
//                .collect(Collectors.toList());
//        if(!CollectionUtils.isEmpty(sysList)){
//            alarmSysCountDataForRedis(sysList);
//        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return  batchCount;
    }

    /**
     * @param []
     * @return void
     * @title alarmCountDataForRedis
     * @description 展示当天（从0点开始计时），近3天，近7天，近1个月，近3个月，近6个月的数据
     * 资源告警：以物理设备（IP）为单位，告警类型为CPU过高，内存不足，磁盘不足、流量过载，统计占比，展示告警次数
     * @author jiruyi
     * @create 2023/4/21 0021 10:15
     */
    @Override
    public void alarmPhyCountDataForRedis(List<AlarmInfo> alarmInfoList) {
        if (CollectionUtils.isEmpty(alarmInfoList)) {
            return;
        }
        //按照ip分组
        Map<String, List<AlarmInfo>> alarmInfoMap = alarmInfoList.stream()
                .collect(Collectors.groupingBy(AlarmInfo::getAlarmSourceIp));
        log.info("alarmPhyCountDataForRedis  alarmInfoMap：{}",alarmInfoMap);
        if (CollectionUtils.isEmpty(alarmInfoMap)) {
            return;
        }
        //处理不同ip数据 redis
        alarmInfoMap.keySet().stream().forEach(ip -> {
            // {"2023-04-13":1-4-2-3,"202304-14":1-4-2-3,"2023-04-15":1-4-2-3},
            Map<Date, String> physicalValueMap = null;
            //查询redis
            if (!redisTemplate.hasKey(ip + PHYSICAL_ALARM_COUNT)) {
                physicalValueMap = new ConcurrentHashMap<>();
            } else {
                physicalValueMap = redisTemplate.opsForHash().entries(ip + PHYSICAL_ALARM_COUNT);
            }
            log.info("ip :{} physicalValueMap：{}",ip,physicalValueMap);
            //每个ip下按照告警时间分组
            Map<Date, List<AlarmInfo>> dateListMap =
                    alarmInfoMap.get(ip).stream().collect(Collectors.groupingBy(alarmInfo -> {
                        return DateUtils.dateToDate(alarmInfo.getAlarmUploadTime());
                    }));
            //统计本次这个时间的条数
            for (Date date : dateListMap.keySet()) {
                //获取各个资源条数
                Map<String, Long> countMap = AlarmPhyConTypeEnum.getCodeCount(dateListMap.get(date), physicalValueMap.get(date));
                //放入redis
                redisTemplate.opsForHash().put(ip + PHYSICAL_ALARM_COUNT,
                        DateUtils.dateToDate(date),
                        countMap.get(AlarmPhyConTypeEnum.CPU.getCode()) + DataConstants.VLINE +
                                countMap.get(AlarmPhyConTypeEnum.MEM.getCode()) + DataConstants.VLINE +
                                countMap.get(AlarmPhyConTypeEnum.DISK.getCode()) + DataConstants.VLINE +
                                countMap.get(AlarmPhyConTypeEnum.FLOW.getCode()));
                //过期时间redis
                redisTemplate.expire(ip + PHYSICAL_ALARM_COUNT+":"+date,190, TimeUnit.DAYS);
            }
        });
    }

    /**
     * @title alarmPhyCountDataForRedis
     * @param [alarmInfoList]
     * @return void
     * @description 展示当天（从0点开始计时），近3天，近7天，近1个月，近3个月，近6个月的数据
     * 系统告警：01 接入  02 边界  11 密钥中心  级别 1严重 2 紧急 3 一般
     * 业务系统告警类型数量统计，包括严重、紧急、一般三种类型，统计占比，展示告警次数
     * @author jiruyi
     * @create 2023/4/23 0023 15:33
     */
    @Override
    public void alarmSysCountDataForRedis(List<AlarmInfo> alarmInfoList) {
        if (CollectionUtils.isEmpty(alarmInfoList)) {
            return;
        }
        //按照来源类型：分组 01 接入  02 边界  11 密钥中心
        Map<String, List<AlarmInfo>> alarmInfoMap = alarmInfoList.stream().collect(Collectors.groupingBy(AlarmInfo::getAlarmSourceType));
        if (CollectionUtils.isEmpty(alarmInfoMap)) {
            return;
        }
        //处理每个来源的数据
        alarmInfoMap.keySet().stream().forEach(sourceType ->{

            // {"2023-04-13":1-4-2,"202304-14":1-4-2,"2023-04-15":1-4-2},
            Map<Date, String> sysValueMap = null;
            //查询redis
            if (!redisTemplate.hasKey(sourceType + SYSTEM_ALARM_COUNT)) {
                sysValueMap = new ConcurrentHashMap<>();
            } else {
                sysValueMap = redisTemplate.opsForHash().entries(sourceType + SYSTEM_ALARM_COUNT);
            }
            log.info("sourceType :{} sysValueMap：{}",sourceType,sysValueMap);
            //每个类型下按照告警时间分组
            Map<Date, List<AlarmInfo>> dateListMap =
                    alarmInfoMap.get(sourceType).stream().collect(Collectors.groupingBy(alarmInfo -> {
                        return DateUtils.dateToDate(alarmInfo.getAlarmUploadTime());
                    }));
            //统计本次每个级别的条数
            for (Date date : dateListMap.keySet()) {
                //获取各个资源条数
                Map<String, Long> countMap = AlarmSysLevelEnum.getCodeCount(dateListMap.get(date), sysValueMap.get(date));
                //放入redis
                redisTemplate.opsForHash().put(sourceType + SYSTEM_ALARM_COUNT,
                        DateUtils.dateToDate(date),
                        countMap.get(AlarmSysLevelEnum.ACCESS.getCode()) + DataConstants.VLINE +
                                countMap.get(AlarmSysLevelEnum.BOUNDARY.getCode()) + DataConstants.VLINE +
                                countMap.get(AlarmSysLevelEnum.KEYCENTER.getCode()));
                //过期时间redis
                redisTemplate.expire(sourceType + SYSTEM_ALARM_COUNT+":"+date,190, TimeUnit.DAYS);
            }
        });

    }
}
