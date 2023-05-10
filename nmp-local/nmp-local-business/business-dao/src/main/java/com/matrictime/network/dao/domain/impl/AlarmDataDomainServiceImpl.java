package com.matrictime.network.dao.domain.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.base.enums.AlarmPhyConTypeEnum;
import com.matrictime.network.base.enums.AlarmSysLevelEnum;
import com.matrictime.network.base.util.DateUtils;
import com.matrictime.network.dao.domain.AlarmDataDomainService;
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
import org.springframework.util.StringUtils;

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
        try {
            //redis  物理资源插入
            List<AlarmInfo> phyList = alarmInfoList.stream().filter(Objects::nonNull)
                    .filter(alarmInfo -> "00".equals(alarmInfo.getAlarmSourceType()))
                    .collect(Collectors.toList());
            alarmPhyCountDataToRedis(phyList);
            //redis  业务系统资源插入
            List<AlarmInfo> sysList = alarmInfoList.stream().filter(Objects::nonNull)
                    .filter(alarmInfo -> !"00".equals(alarmInfo.getAlarmSourceType()))
                    .collect(Collectors.toList());
            alarmSysCountDataToRedis(sysList);
        } catch (Exception e) {
            log.error("acceptAlarmData insert redis error:{}", e);
            return -1;
        }
        //mysql 插入
        return alarmInfoExtMapper.batchInsert(alarmInfoList);
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
    public void alarmPhyCountDataToRedis(List<AlarmInfo> alarmInfoList) {
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
            // {"2023-04-13":1-4-2-3,"-20230414":1-4-2-3,"2023-04-15":1-4-2-3},
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
                log.info("此次 ip:{}上传的物理资源告警各个类型的条数是:{}", ip, countMap);
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
        /**根据小区查询所有类型 设备id*/
        List<Map> idMap = alarmInfoExtMapper.selectDeviceIdFromAllTables();
        if (CollectionUtils.isEmpty(idMap)) {
            return null;
        }
        /**从redis查询*/
        Map<String, Map<String, Long>> redisMap = querySysAlarmCountFromRedis(alarmDataBaseRequest, idMap);
        if (!CollectionUtils.isEmpty(redisMap)) {
            return redisMap;
        }
        CountDownLatch countDownLatch = new CountDownLatch(3);
        //结果map   // {"access":{"seriousCount":2,"emergentCount":14,"3":27,"sameAsCount":3}}
        Map<String, Map<String, Long>> resultMap = new ConcurrentHashMap<>();
        //查询各个类型（接入  边界  密钥中心）
        log.info("querySysAlarmDataCount begin time :{}", System.currentTimeMillis());
        Arrays.stream(AlarmSysLevelEnum.values()).forEach(alarmSysLevelEnum -> {
            taskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    /**入参map*/
                    Map<String, String> paramMap = getParamMap(alarmDataBaseRequest);
                    paramMap.put("columnName", alarmSysLevelEnum.getColumnName());//列名
                    paramMap.put("tableName", alarmSysLevelEnum.getTableName());//表名
                    paramMap.put("alarmSourceType", alarmSysLevelEnum.getType());//类型
                    /**{"1":2,"2":14,"3":27} 级别对应条数[{"alarmLevel":"1","countLevel":"2"},
                     {"alarmLevel":"2","countLevel":"2"},{"alarmLevel":"3","countLevel":"2"}]*/
                    List<Map> maps = alarmInfoExtMapper.selectSysAlarmCount(paramMap);
                    /**list 转为map {"seriousCount":2,"emergentCount":14,"sameAsCount":27*/
                    Map<String, Long> countMapResp = new ConcurrentHashMap<>();
                    if (!CollectionUtils.isEmpty(maps)) {
                        countMapResp = maps.stream().collect(Collectors.toMap(
                                entry -> AlarmSysLevelEnum.LevelEnum.getCodeBylevel(String.valueOf(entry.get("alarmLevel"))),
                                entry -> Objects.isNull(entry.get("countLevel")) ? 0 : Long.valueOf(entry.get("countLevel").toString())));
                        log.info("线程:{}查询:{}的结果maps:{}", Thread.currentThread().getName(),
                                alarmSysLevelEnum.getDesc(), maps);
                        log.info("线程:{}的结果codeMapResp:{}", Thread.currentThread().getName(), countMapResp);
                        /**每个类型基站的结果放入resultMap*/
                        resultMap.put(alarmSysLevelEnum.getCode(), countMapResp);
                    }
                    countDownLatch.countDown();
                }
            });
        });
        log.info("AlarmDataDomainService querySysAlarmData resultMap:{}", resultMap);
        countDownLatch.await();
        log.info("querySysAlarmDataCount end time :{}", System.currentTimeMillis());
        return resultMap;
    }

    /**
     * @param [alarmDataBaseRequest, ips]
     * @return java.util.List<java.util.Map>
     * @title querSysAlarmCountFromRedis
     * @description
     * @author jiruyi
     * @create 2023/5/10 0010 10:14
     */
    Map<String, Map<String, Long>> querySysAlarmCountFromRedis(AlarmDataBaseRequest alarmDataBaseRequest, List<Map> idMap) {
        /**归类每一个类型 01 02 11*/
        Map<String, List<Map>> maps = idMap.stream().collect(Collectors.groupingBy(map -> String.valueOf(map.get("deviceType"))));
        /**2 在redis查询所有类型*/
        Map<String, Map<String, Long>> resultMap = new ConcurrentHashMap<>();
        for (String deviceType : maps.keySet()) {
            /**收集该类型所有id*/
            List<String> ids = maps.get(deviceType).stream().map(map -> String.valueOf(map.get("deviceId")))
                    .collect(Collectors.toList());
            Long seriousCount = 0l, emergentCount = 0l, sameAsCount = 0l;
            Map<String, Long> countMap = new ConcurrentHashMap<>();
            for (String id : ids) {
                /**获取该设备的hash value*/
                Map<Date, String> systemValueMap = redisTemplate.opsForHash().entries(id + SYSTEM_ALARM_COUNT);
                /**该id redis没有对应value*/
                if (CollectionUtils.isEmpty(systemValueMap)) {
                    continue;
                }
                /**3 获取value中每个日期*/
                for (int i = 0; i <= Integer.valueOf(alarmDataBaseRequest.getTimePicker()); i++) {
                    /** 从当前日期往前推 ,.timepicker(0  3 7 30 90 180)天 去除时分秒*/
                    Date everyDate = DateUtils.dateToDate(DateUtils.addDayForNow(-i));
                    String dateValue = systemValueMap.get(everyDate);
                    if (StringUtils.isEmpty(dateValue)) {
                        continue;
                    }
                    String[] countArray = dateValue.split(DataConstants.VLINE);
                    if (CollectionUtils.isEmpty(Arrays.asList(countArray)) || countArray.length < values().length) {
                        continue;
                    }
                    /**4 累加每个日期的值*/
                    seriousCount += Long.valueOf(countArray[AlarmSysLevelEnum.LevelEnum.SERIOUS.getRedisIndex()]);
                    emergentCount += Long.valueOf(countArray[AlarmSysLevelEnum.LevelEnum.EMERG.getRedisIndex()]);
                    sameAsCount += Long.valueOf(countArray[AlarmSysLevelEnum.LevelEnum.SAMEAS.getRedisIndex()]);
                }
            }
            if((seriousCount+emergentCount+sameAsCount) != 0L){
                countMap.put(AlarmSysLevelEnum.LevelEnum.SERIOUS.getCode(), seriousCount);
                countMap.put(AlarmSysLevelEnum.LevelEnum.EMERG.getCode(), emergentCount);
                countMap.put(AlarmSysLevelEnum.LevelEnum.SAMEAS.getCode(), sameAsCount);
                resultMap.put(deviceType, countMap);
            }
        }
        return resultMap;
    }

    /**
     * @param [alarmDataBaseRequest]
     * @return java.util.List<java.util.Map>
     * @title queryPhyAlarmCountFromRedis
     * @description 从redis 查询物理设备告警条数 key: 192.168.72.24:physical_alarm_count
     * value: {"["java.util.Date",1683302400000]":"0-0-0-2"}
     * @author jiruyi
     * @create 2023/5/5 0005 10:28
     */
    List<Map> queryPhyAlarmCountFromRedis(AlarmDataBaseRequest alarmDataBaseRequest, List<String> ips) {
        if (CollectionUtils.isEmpty(ips)) {
            return null;
        }
        /**2 在redis查询所有ip*/
        List<Map> resultList = new ArrayList<>();
        for (String ip : ips) {
            /**获取该设备的hash value*/
            Map<Date, String> physicalValueMap = redisTemplate.opsForHash().entries(ip + PHYSICAL_ALARM_COUNT);
            /**该ip redis没有对应value*/
            if (CollectionUtils.isEmpty(physicalValueMap)) {
                continue;
            }
            Long cpuCount = 0l, memCount = 0l, diskCount = 0l, flowCount = 0l;
            Map<String, Long> countMap = new ConcurrentHashMap<>();
            /**3 获取value中每个日期*/
            for (int i = 0; i <= Integer.valueOf(alarmDataBaseRequest.getTimePicker()); i++) {
                /** 从当前日期往前推 ,.timepicker(0  3 7 30 90 180)天 去除时分秒*/
                Date everyDate = DateUtils.dateToDate(DateUtils.addDayForNow(-i));
                String dateValue = physicalValueMap.get(everyDate);
                if (StringUtils.isEmpty(dateValue)) {
                    continue;
                }
                String[] countArray = dateValue.split(DataConstants.VLINE);
                if (CollectionUtils.isEmpty(Arrays.asList(countArray)) || countArray.length < values().length) {
                    continue;
                }
                /**4 累加每个日期的值*/
                cpuCount += Integer.valueOf(countArray[CPU.getRedisIndex()]);
                memCount += Integer.valueOf(countArray[MEM.getRedisIndex()]);
                diskCount += Integer.valueOf(countArray[DISK.getRedisIndex()]);
                flowCount += Integer.valueOf(countArray[FLOW.getRedisIndex()]);
            }
            if((cpuCount+memCount+diskCount+flowCount)!=0){
                countMap.put(CPU.getContentType(), cpuCount);
                countMap.put(MEM.getContentType(), memCount);
                countMap.put(DISK.getContentType(), diskCount);
                countMap.put(FLOW.getContentType(), flowCount);
            }

            /**构造与mysql相同的数据结构*/
            buildResultList(ip, countMap, resultList);
        }
        return resultList;
    }

    /**
     * @param [ip, countMap, resultList]
     * @return void
     * @title buildResultList
     * @description
     * @author jiruyi
     * @create 2023/5/9 0009 20:54
     */
    public void buildResultList(String ip, Map<String, Long> countMap, List<Map> resultList) {
        for (AlarmPhyConTypeEnum conTypeEnum : AlarmPhyConTypeEnum.values()) {
            Map map = new ConcurrentHashMap();
            map.put("sourceIp", ip);
            map.put("contentType", conTypeEnum.getContentType());
            map.put("typeCount", Objects.isNull(countMap.get(conTypeEnum.getContentType())) ? 0 : countMap.get(conTypeEnum.getContentType()));
            resultList.add(map);
        }
    }

    /**
     * @param [mapList]
     * @return java.util.List<java.util.Map>
     * @title mergeListByContentType
     * @description 合并类型 把所有告警类型相同的map 相加count后合并
     * @author jiruyi
     * @create 2023/5/9 0009 9:28
     */
    public List<Map> mergeListByContentType(List<Map> mapList) {
        if (CollectionUtils.isEmpty(mapList)) {
            return null;
        }
        List<Map> list = new ArrayList<>();
        Map<String, List<Map>> maps = mapList.stream().collect(Collectors.groupingBy(map -> String.valueOf(map.get("contentType"))));
        maps.keySet().stream().forEach(conType -> {
            long count = maps.get(conType).stream().collect(Collectors.summingLong(map -> parseToLong(map.get("typeCount"))));
            Map map = new ConcurrentHashMap();
            map.put("sourceIp", maps.get(conType).get(0).get("sourceIp"));
            map.put("contentType", conType);
            map.put("typeCount", count);
            list.add(map);
        });
        return list;
    }

    public Long parseToLong(Object obj) {
        if (Objects.isNull(obj)) {
            return 0L;
        }
        return Long.parseLong(String.valueOf(obj));
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
        List<Map> dataSourceMapList = null;
        /** 查询小区下所有ip*/
        List<String> ips = alarmInfoExtMapper.selectIpFromDeviceAndStation(alarmDataBaseRequest.getRoId());
        try {
            /**查询redis*/
            dataSourceMapList = queryPhyAlarmCountFromRedis(alarmDataBaseRequest, ips);
        } catch (Exception e) {
            log.info("queryPhyAlarmCountFromRedis exception :{}", e);
        }
        /**数据库查询 */
        if (CollectionUtils.isEmpty(dataSourceMapList)) {
            dataSourceMapList = alarmInfoExtMapper.selectPhyAlarmCount(paramMap);
        }
        if (CollectionUtils.isEmpty(dataSourceMapList)) {
            return null;
        }
        log.info("AlarmDataDomainService queryPhyAlarmDataCount mybatisMapList:{}", dataSourceMapList);
        /** 结果转换  ip 分组 */
        Map<String, List<Map>> alarmInfoMap = dataSourceMapList.stream()
                .collect(Collectors.groupingBy(entry -> String.valueOf(entry.get("sourceIp"))));
        List<AlarmPhyTypeCount> phyTypeCountList = new ArrayList<>();
        alarmInfoMap.entrySet().forEach(entry -> {
            /**[{"sourceIp":"192.168.72.241","contentType":"1","count":"2"},{"sourceIp":"192.168.72.241","contentType":"2","count":"2"}]*/
            List<Map> ipList = entry.getValue();
            AlarmPhyTypeCount phyTypeCount = new AlarmPhyTypeCount();
            for (Map map : ipList) {
                if (CollectionUtils.isEmpty(map)) {
                    continue;
                }
                /**获取告警类型*/
                String contentType = (String) map.get("contentType");
                /**获取告警类型对应条数*/
                Long count = Objects.isNull(map.get("typeCount")) ? 0 : Long.valueOf(String.valueOf(map.get("typeCount")));
                phyTypeCount.setPhyIp(String.valueOf(map.get("sourceIp")));
                switch (AlarmPhyConTypeEnum.getEnumByContentType(contentType)) {
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
            phyTypeCountList.add(phyTypeCount);
        });
        /**补全没有数据的物理设备 默认都是0*/
        ips.removeAll(phyTypeCountList.stream().map(AlarmPhyTypeCount::getPhyIp).collect(Collectors.toList()));
        ips.stream().forEach(ip -> {
            phyTypeCountList.add(new AlarmPhyTypeCount(ip));
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
        log.info("AlarmDataDomainService  queryAlarmDataList page:{},nmplAlarmInfos：{}", page, nmplAlarmInfos);
        return new PageInfo<>((int) page.getTotal(), page.getPages(), nmplAlarmInfos);
    }

    /**
     * @param [alarmDataBaseRequest]
     * @return java.util.Map<java.lang.String, java.lang.String>
     * @title getParamMap
     * @description 公共参数
     * @author jiruyi
     * @create 2023/4/25 0025 16:42
     */
    public synchronized Map<String, String> getParamMap(AlarmDataBaseRequest alarmDataBaseRequest) {
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
    public void alarmSysCountDataToRedis(List<AlarmInfo> alarmInfoList) {
        if (CollectionUtils.isEmpty(alarmInfoList)) {
            return;
        }
        //按照id：分组
        Map<String, List<AlarmInfo>> alarmInfoMap = alarmInfoList.stream().collect(Collectors.groupingBy(AlarmInfo::getAlarmSourceId));
        if (CollectionUtils.isEmpty(alarmInfoMap)) {
            return;
        }
        //处理每个id的数据
        alarmInfoMap.keySet().stream().forEach(sourceId -> {
            // {"2023-04-13":1-4-2,"202304-14":1-4-2,"2023-04-15":1-4-2},
            Map<Date, String> sysValueMap = null;
            //查询redis
            if (!redisTemplate.hasKey(sourceId + SYSTEM_ALARM_COUNT)) {
                sysValueMap = new ConcurrentHashMap<>();
            } else {
                sysValueMap = redisTemplate.opsForHash().entries(sourceId + SYSTEM_ALARM_COUNT);
            }
            log.info("sourceId :{} sysValueMap：{}", sourceId, sysValueMap);
            //每个设备下按照告警时间分组  {"2023-04-13":1-4-2,"202304-14":1-4-2,"2023-04-15":1-4-2},
            Map<Date, List<AlarmInfo>> dateListMap =
                    alarmInfoMap.get(sourceId).stream().collect(Collectors.groupingBy(alarmInfo -> {
                        return DateUtils.dateToDate(alarmInfo.getAlarmUploadTime());
                    }));
            //统计本次每个级别的条数
            for (Date date : dateListMap.keySet()) {
                //获取各个资源条数
                Map<String, Long> countMap = AlarmSysLevelEnum.getCodeCount(dateListMap.get(date), sysValueMap.get(date));
                //放入redis
                redisTemplate.opsForHash().put(sourceId + SYSTEM_ALARM_COUNT,
                        DateUtils.dateToDate(date),
                        countMap.get(AlarmSysLevelEnum.LevelEnum.SERIOUS.getLevel()) + DataConstants.VLINE +
                                countMap.get(AlarmSysLevelEnum.LevelEnum.EMERG.getLevel()) + DataConstants.VLINE +
                                countMap.get(AlarmSysLevelEnum.LevelEnum.SAMEAS.getLevel()));
                //过期时间redis
                redisTemplate.expire(sourceId + SYSTEM_ALARM_COUNT + ":" + date, 190, TimeUnit.DAYS);
            }
        });

    }
}
