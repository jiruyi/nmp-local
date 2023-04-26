package com.matrictime.network.dao.domain.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.base.enums.AlarmPhyConTypeEnum;
import com.matrictime.network.base.enums.AlarmSysLevelEnum;
import com.matrictime.network.base.util.DateUtils;
import com.matrictime.network.dao.domain.AlarmDataDomainService;
import com.matrictime.network.dao.mapper.NmplAlarmInfoMapper;
import com.matrictime.network.dao.mapper.extend.NmplAlarmInfoExtMapper;
import com.matrictime.network.dao.model.NmplAlarmInfo;
import com.matrictime.network.model.AlarmInfo;
import com.matrictime.network.request.AlarmDataBaseRequest;
import com.matrictime.network.request.AlarmDataListReq;
import com.matrictime.network.response.AlarmPhyTypeCount;
import com.matrictime.network.response.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.matrictime.network.base.constant.RedisKeyConstants.PHYSICAL_ALARM_COUNT;
import static com.matrictime.network.base.constant.RedisKeyConstants.SYSTEM_ALARM_COUNT;
import static com.matrictime.network.base.enums.AlarmPhyConTypeEnum.*;

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
        int batchCount = alarmInfoExtMapper.batchInsert(alarmInfoList);
        //redis 插入
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                //redis  物理资源插入
                List<AlarmInfo> phyList = alarmInfoList.stream().filter(Objects::nonNull)
                        .filter(alarmInfo -> "00".equals(alarmInfo.getAlarmSourceType()))
                        .collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(phyList)) {
                    alarmPhyCountDataForRedis(phyList);
                }
                //redis  业务系统资源插入 todo 暂时不用redis
//                List<AlarmInfo> sysList = alarmInfoList.stream().filter(Objects::nonNull)
//                        .filter(alarmInfo -> !"00".equals(alarmInfo.getAlarmSourceType()))
//                        .collect(Collectors.toList());
//                if(!CollectionUtils.isEmpty(sysList)){
//                    alarmSysCountDataForRedis(sysList);
//                }
            }
        });
        return batchCount;
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
        log.info("alarmPhyCountDataForRedis  alarmInfoMap：{}", alarmInfoMap);
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
            log.info("ip :{} physicalValueMap：{}", ip, physicalValueMap);
            //每个ip下按照告警时间分组
            Map<Date, List<AlarmInfo>> dateListMap =
                    alarmInfoMap.get(ip).stream().collect(Collectors.groupingBy(alarmInfo -> {
                        return DateUtils.dateToDate(alarmInfo.getAlarmUploadTime());
                    }));
            //统计本次这个时间的条数
            for (Date date : dateListMap.keySet()) {
                //获取各个资源条数
                Map<String, Long> countMap = getCodeCount(dateListMap.get(date), physicalValueMap.get(date));
                //放入redis
                redisTemplate.opsForHash().put(ip + PHYSICAL_ALARM_COUNT,
                        DateUtils.dateToDate(date),
                        countMap.get(CPU.getCode()) + DataConstants.VLINE +
                                countMap.get(MEM.getCode()) + DataConstants.VLINE +
                                countMap.get(DISK.getCode()) + DataConstants.VLINE +
                                countMap.get(FLOW.getCode()));
                //过期时间redis
                redisTemplate.expire(ip + PHYSICAL_ALARM_COUNT + ":" + date, 190, TimeUnit.DAYS);
            }
        });
    }

    /**
     * @param [alarmDataBaseRequest]
     * @return java.util.Map
     * @title querySysAlarmDataList
     * @description 查询业务系统告警条数
     * @author jiruyi
     * @create 2023/4/24 0024 14:25
     */
    @Override
    public Map<String, Map<String, Long>> querySysAlarmDataCount(AlarmDataBaseRequest alarmDataBaseRequest) throws Exception {
        //入参map
        Map<String, String> paramMap = getParamMap(alarmDataBaseRequest);
        ;
        CountDownLatch countDownLatch = new CountDownLatch(3);
        //结果map   // {"access":{"seriousCount":2,"emergentCount":14,"3":27,"sameAsCount":3}}
        Map<String, Map<String, Long>> resultMap = new ConcurrentHashMap<>();
        //查询各个类型（接入  边界  密钥中心）
        Arrays.stream(AlarmSysLevelEnum.values()).forEach(alarmSysLevelEnum -> {
            taskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    paramMap.put("columnName", alarmSysLevelEnum.getColumnName());//列名
                    paramMap.put("tableName", alarmSysLevelEnum.getTableName());//表名
                    paramMap.put("alarmSourceType", alarmSysLevelEnum.getType());//类型
                    //{"1":2,"2":14,"3":27} 级别对应条数
                    //[{"alarmLevel":"1","countLevel":"2"},{"alarmLevel":"2","countLevel":"2"},{"alarmLevel":"3","countLevel":"2"}]
                    List<Map> maps = alarmInfoExtMapper.selectSysAlarmCount(paramMap);
                    //list 转为map {"seriousCount":2,"emergentCount":14,"sameAsCount":27
                    Map<String, Long> countMapResp = new ConcurrentHashMap<>();
                    if (!CollectionUtils.isEmpty(maps)) {
                        countMapResp = maps.stream().collect(Collectors.toMap(
                                entry -> AlarmSysLevelEnum.LevelEnum.getKeyCodeBylevel(String.valueOf(entry.get("alarmLevel"))),
                                entry -> Long.valueOf(entry.get("countLevel").toString())));
                        log.info("线程:{}查询:{}的结果maps:{}", Thread.currentThread().getName(),
                                alarmSysLevelEnum.getDesc(), maps);
                        log.info("线程:{}的结果codeMapResp:{}", Thread.currentThread().getName(), countMapResp);
                        //每个类型基站的结果放入resultMap
                        resultMap.put(alarmSysLevelEnum.getCode(), countMapResp);
                    }
                    countDownLatch.countDown();
                }
            });
        });
        log.info("AlarmDataDomainService querySysAlarmData resultMap:{}", resultMap);
        countDownLatch.await();
        return resultMap;
    }

    /**
     * @param [alarmDataBaseRequest]
     * @return java.util.List<java.util.Map>
     * @title queryPhyAlarmDataCount
     * @description 根据 小区 时间范围查询所有物理设备告警条数
     * @author jiruyi
     * @create 2023/4/25 0025 14:52
     */
    @Override
    public List<AlarmPhyTypeCount> queryPhyAlarmDataCount(AlarmDataBaseRequest alarmDataBaseRequest) {
        /**入参map */
        Map<String, String> paramMap = getParamMap(alarmDataBaseRequest);
        /**数据库查询 */
        List<Map> mybatisMapList = alarmInfoExtMapper.selectPhyAlarmCount(paramMap);
        if (CollectionUtils.isEmpty(mybatisMapList)) {
            return null;
        }
        log.info("AlarmDataDomainService queryPhyAlarmDataCount mybatisMapList:{}", mybatisMapList);
        /** 结果转换  ip 分组 */
        Map<String, List<Map>> alarmInfoMap = mybatisMapList.stream()
                .collect(Collectors.groupingBy(entry -> String.valueOf(entry.get("sourceIp"))));
        List<AlarmPhyTypeCount> phyTypeCountList = new ArrayList<>();
        alarmInfoMap.entrySet().forEach(entry -> {
            /**[{"sourceIp":"192.168.72.241","contentType":"1","count":"2"},{"sourceIp":"192.168.72.241","contentType":"2","count":"2"}]*/
            List<Map> ipList = entry.getValue();
            AlarmPhyTypeCount phyTypeCount = new AlarmPhyTypeCount();
            ;
            if (!CollectionUtils.isEmpty(ipList)) {
                for (Map map : ipList) {
                    String contentType = (String) map.get("contentType");
                    Long count = Long.valueOf(String.valueOf(map.get("count")));
                    phyTypeCount.setPhyIp(String.valueOf(map.get("sourceIp")));
                    switch (AlarmPhyConTypeEnum.getBycode(contentType)) {
                        case CPU:
                            phyTypeCount.setCpuCount(count);
                            break;
                        case MEM:
                            phyTypeCount.setMemCount(count);
                            break;
                        case DISK:
                            phyTypeCount.setDiskCount(count);
                            break;
                        case FLOW:
                            phyTypeCount.setFlowCount(count);
                            break;
                        default:
                    }
                }
            }
            phyTypeCountList.add(phyTypeCount);
        });
        return phyTypeCountList;
    }

    /**
     * @param [alarmDataListReq]
     * @return java.util.List<com.matrictime.network.dao.model.NmplAlarmInfo>
     * @title queryAlarmDataList
     * @description 查询告警信息列表
     * @author jiruyi
     * @create 2023/4/26 0026 15:19
     */
    @Override
    public PageInfo<NmplAlarmInfo> queryAlarmDataList(AlarmDataListReq alarmDataListReq) {
        //分页
        Page<NmplAlarmInfo> page = PageHelper.startPage(alarmDataListReq.getPageNo(), alarmDataListReq.getPageSize());
        List<NmplAlarmInfo> nmplAlarmInfos = alarmInfoExtMapper.queryAlarmDataList(alarmDataListReq);
        log.info("AlarmDataDomainService  queryAlarmDataList page:{},nmplAlarmInfos",page,nmplAlarmInfos);
        return  new PageInfo<>((int)page.getTotal(),page.getPages(),nmplAlarmInfos);
    }

    /**
     * @param [alarmDataBaseRequest]
     * @return java.util.Map<java.lang.String, java.lang.String>
     * @title getParamMap
     * @description 公共参数
     * @author jiruyi
     * @create 2023/4/25 0025 16:42
     */
    public Map<String, String> getParamMap(AlarmDataBaseRequest alarmDataBaseRequest) {
        //入参map
        Map<String, String> paramMap = new ConcurrentHashMap<>();
        paramMap.put("roId", alarmDataBaseRequest.getRoId());
        paramMap.put("timePicker", alarmDataBaseRequest.getTimePicker());
        return paramMap;
    }

    /**
     * @param [alarmInfoList]
     * @return void
     * @title alarmPhyCountDataForRedis
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
        //处理每个来源的数据 01 接入  02 边界  11 密钥中心
        alarmInfoMap.keySet().stream().forEach(sourceType -> {

            // {"2023-04-13":1-4-2,"202304-14":1-4-2,"2023-04-15":1-4-2},
            Map<Date, String> sysValueMap = null;
            //查询redis
            if (!redisTemplate.hasKey(sourceType + SYSTEM_ALARM_COUNT)) {
                sysValueMap = new ConcurrentHashMap<>();
            } else {
                sysValueMap = redisTemplate.opsForHash().entries(sourceType + SYSTEM_ALARM_COUNT);
            }
            log.info("sourceType :{} sysValueMap：{}", sourceType, sysValueMap);
            //每个类型下按照告警时间分组  {"2023-04-13":1-4-2,"202304-14":1-4-2,"2023-04-15":1-4-2},
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
                        countMap.get(AlarmSysLevelEnum.LevelEnum.SERIOUS.getLevel()) + DataConstants.VLINE +
                                countMap.get(AlarmSysLevelEnum.LevelEnum.EMERG.getLevel()) + DataConstants.VLINE +
                                countMap.get(AlarmSysLevelEnum.LevelEnum.SAMEAS.getLevel()));
                //过期时间redis
                redisTemplate.expire(sourceType + SYSTEM_ALARM_COUNT + ":" + date, 190, TimeUnit.DAYS);
            }
        });

    }
}
