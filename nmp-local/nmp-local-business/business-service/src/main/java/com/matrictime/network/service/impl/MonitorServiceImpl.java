package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.SystemException;
import com.matrictime.network.base.enums.AlarmPhyConTypeEnum;
import com.matrictime.network.base.enums.AlarmSysLevelEnum;
import com.matrictime.network.constant.DataConstants;
import com.matrictime.network.dao.mapper.*;
import com.matrictime.network.dao.mapper.extend.NmplDataCollectExtMapper;
import com.matrictime.network.dao.mapper.extend.NmplDeviceExtMapper;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.exception.ErrorCode;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.model.AlarmInfo;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.*;
import com.matrictime.network.request.*;
import com.matrictime.network.response.*;
import com.matrictime.network.service.AlarmDataService;
import com.matrictime.network.service.BaseStationInfoService;
import com.matrictime.network.service.DeviceService;
import com.matrictime.network.service.MonitorService;
import com.matrictime.network.util.CompareUtil;
import com.matrictime.network.util.DateUtils;
import com.matrictime.network.util.ParamCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.matrictime.network.base.constant.DataConstants.*;
import static com.matrictime.network.base.exception.ErrorMessageContants.DEVICE_NOT_EXIST_MSG;
import static com.matrictime.network.constant.BusinessConsts.*;
import static com.matrictime.network.constant.DataConstants.*;
import static com.matrictime.network.util.DateUtils.MINUTE_TIME_FORMAT;

@Slf4j
@Service
@PropertySource(value = "classpath:/businessConfig.properties",encoding = "UTF-8")
public class MonitorServiceImpl extends SystemBaseService implements MonitorService {

    @Value("${health.deadline.time}")
    private Long healthDeadlineTime;

    @Value("${error.cpu}")
    private String errorCpu;

    @Value("${warn.cpu}")
    private String warnCpu;

    @Value("${info.cpu}")
    private String infoCpu;

    @Value("${error.mem}")
    private String errorMem;

    @Value("${warn.mem}")
    private String warnMem;

    @Value("${info.mem}")
    private String infoMem;

    @Value("${error.disk}")
    private String errorDisk;

    @Value("${warn.disk}")
    private String warnDisk;

    @Value("${info.disk}")
    private String infoDisk;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired(required = false)
    private NmplBaseStationInfoMapper nmplBaseStationInfoMapper;

    @Autowired(required = false)
    private NmplDeviceInfoMapper nmplDeviceInfoMapper;

    @Autowired(required = false)
    private NmplDeviceExtMapper nmplDeviceExtMapper;

    @Autowired(required = false)
    private NmplDataCollectExtMapper nmplDataCollectExtMapper;

    @Autowired(required = false)
    private NmplDataCollectMapper nmplDataCollectMapper;

    @Autowired
    private BaseStationInfoService baseStationInfoService;

    @Autowired
    private DeviceService deviceService;

    @Resource
    private NmplPhysicalDeviceHeartbeatMapper nmplPhysicalDeviceHeartbeatMapper;

    @Resource
    private NmplPhysicalDeviceResourceMapper nmplPhysicalDeviceResourceMapper;

    @Resource
    private NmplSystemResourceMapper nmplSystemResourceMapper;

    @Autowired
    private AlarmDataService alarmDataService;


    private static final String USER_COUNT_CODE = "userNumber";
    private static final String TOTAL_BAND_WIDTH_CODE = "bandwidth";

    // TODO: 2022/6/27 上线前需要确认配置信息
    private static final int USER_COUNT_TIME = 15;
    private static final int TOTAL_BAND_WIDTH_TIME = 15;
    private static final int SPLIT_TIME = 15;


    /**
     * 设备状态映射
     */
    public static final Map<String,String> deviceMap = new HashMap<String,String>() ;

    static {
       deviceMap.put("0",STATION_STATUS_ACTIVE);
       deviceMap.put("1",STATION_STATUS_ABNORMAL);
    }


    @Override
    public Result<CheckHeartResp> checkHeart(CheckHeartReq req) {
        Result result;
        CheckHeartResp resp = null;
        try {
            checkHeartParam(req);
            Map<String, String> map = checkStationStatus(req.getDeviceId());
            redisTemplate.opsForValue().set(HEART_CHECK_DEVICE_ID+req.getDeviceId(),true,healthDeadlineTime, TimeUnit.SECONDS);
            String status = map.get("status");
            String bigType = map.get("bigType");
            String id = map.get("id");
            if(!deviceMap.get(req.getStatus()).equals(status)){
                heartHttpPush(bigType,id, req.getDeviceId(),deviceMap.get(req.getStatus()));
            }
            result = buildResult(resp);
        }catch (Exception e){
            log.error("MonitorServiceImpl.checkHeart Exception:{}",e.getMessage());
            result = failResult(e);
        }

        return result;
    }

    /**
     * 物理设备心跳上报
     * @param req
     * @return
     */
    @Override
    public Result physicalDeviceHeartbeat(PhysicalDeviceHeartbeatReq req) {
        Result result;
        try{
            List<PhysicalDeviceHeartbeatVo> heartbeatList = req.getHeartbeatList();
            for (PhysicalDeviceHeartbeatVo vo : heartbeatList){
                NmplPhysicalDeviceHeartbeat dto = new NmplPhysicalDeviceHeartbeat();
                BeanUtils.copyProperties(vo,dto);
                dto.setUploadTime(req.getUploadTime());
                NmplPhysicalDeviceHeartbeat heartbeat = nmplPhysicalDeviceHeartbeatMapper.selectByPrimaryKey(vo.getIp1Ip2());
                if (heartbeat !=null){
                    nmplPhysicalDeviceHeartbeatMapper.updateByPrimaryKeySelective(dto);
                }else {
                    nmplPhysicalDeviceHeartbeatMapper.insertSelective(dto);
                }
            }
            result = buildResult(null);
        }catch (Exception e){
            log.error("MonitorServiceImpl.physicalDeviceHeartbeat Exception:{}",e.getMessage());
            result = failResult(e);
        }
        return result;
    }

    /**
     * 物理设备资源情况信息上报
     * @param req
     * @return
     */
    @Override
    public Result physicalDeviceResource(PhysicalDeviceResourceReq req) {
        Result result;
        try{
            List<PhysicalDeviceResourceVo> pdrList = req.getPdrList();
//            List<AlarmInfo> alarmInfoList = new ArrayList<>();
            for (PhysicalDeviceResourceVo vo : pdrList){
                NmplPhysicalDeviceResource dto = new NmplPhysicalDeviceResource();
                BeanUtils.copyProperties(vo,dto);
                NmplPhysicalDeviceResource resource = nmplPhysicalDeviceResourceMapper.selectByPrimaryKey(vo.getDeviceIp(), vo.getResourceType());
                if (resource != null){
                    nmplPhysicalDeviceResourceMapper.updateByPrimaryKeySelective(dto);
                }else {
                    nmplPhysicalDeviceResourceMapper.insertSelective(dto);
                }
//                AlarmInfo alarmInfo = getAlarmInfo(dto);
//                if (alarmInfo != null){
//                    alarmInfoList.add(alarmInfo);
//                }
            }
            // 资源告警推送（转移到代理端）
//            if (!CollectionUtils.isEmpty(alarmInfoList)){
//                alarmDataService.acceptAlarmData(alarmInfoList);
//            }
            result = buildResult(null);
        }catch (Exception e){
            log.error("MonitorServiceImpl.physicalDeviceResource Exception:{}",e.getMessage());
            result = failResult(e);
        }
        return result;
    }

//    private AlarmInfo getAlarmInfo(NmplPhysicalDeviceResource dto){
//        boolean isAlarm = false;
//        AlarmInfo alarmInfo = null;
//        String percent = dto.getResourcePercent();
//        switch (dto.getResourceType()){
//            case RESOURCE_TYPE_CPU :
//                if (CompareUtil.compareShortStr(percent,infoCpu)>1){
//                    isAlarm = true;
//                    alarmInfo.setAlarmContentType(AlarmPhyConTypeEnum.CPU.getContentType());
//                    alarmInfo.setAlarmContent(AlarmPhyConTypeEnum.CPU.getDesc());
//                    if (CompareUtil.compareShortStr(percent,errorCpu)>1){
//                        alarmInfo.setAlarmLevel(AlarmSysLevelEnum.LevelEnum.SERIOUS.getLevel());
//                    }else if (CompareUtil.compareShortStr(percent,warnCpu)>1){
//                        alarmInfo.setAlarmLevel(AlarmSysLevelEnum.LevelEnum.EMERG.getLevel());
//                    }else {
//                        alarmInfo.setAlarmLevel(AlarmSysLevelEnum.LevelEnum.SAMEAS.getLevel());
//                    }
//                }
//                break;
//            case RESOURCE_TYPE_MEMORY:
//                if (CompareUtil.compareShortStr(percent,infoMem)>1){
//                    isAlarm = true;
//                    alarmInfo.setAlarmContentType(AlarmPhyConTypeEnum.MEM.getContentType());
//                    alarmInfo.setAlarmContent(AlarmPhyConTypeEnum.MEM.getDesc());
//                    if (CompareUtil.compareShortStr(percent,errorMem)>1){
//                        alarmInfo.setAlarmLevel(AlarmSysLevelEnum.LevelEnum.SERIOUS.getLevel());
//                    }else if (CompareUtil.compareShortStr(percent,warnMem)>1){
//                        alarmInfo.setAlarmLevel(AlarmSysLevelEnum.LevelEnum.EMERG.getLevel());
//                    }else {
//                        alarmInfo.setAlarmLevel(AlarmSysLevelEnum.LevelEnum.SAMEAS.getLevel());
//                    }
//                }
//                break;
//            case RESOURCE_TYPE_DISK:
//                if (CompareUtil.compareShortStr(percent,infoDisk)>1){
//                    isAlarm = true;
//                    alarmInfo.setAlarmContentType(AlarmPhyConTypeEnum.DISK.getContentType());
//                    alarmInfo.setAlarmContent(AlarmPhyConTypeEnum.DISK.getDesc());
//                    if (CompareUtil.compareShortStr(percent,errorDisk)>1){
//                        alarmInfo.setAlarmLevel(AlarmSysLevelEnum.LevelEnum.SERIOUS.getLevel());
//                    }else if (CompareUtil.compareShortStr(percent,warnDisk)>1){
//                        alarmInfo.setAlarmLevel(AlarmSysLevelEnum.LevelEnum.EMERG.getLevel());
//                    }else {
//                        alarmInfo.setAlarmLevel(AlarmSysLevelEnum.LevelEnum.SAMEAS.getLevel());
//                    }
//                }
//                break;
//            default:
//                break;
//        }
//        if (isAlarm){
//            alarmInfo.setAlarmSourceIp(dto.getDeviceIp());
//            alarmInfo.setAlarmUploadTime(dto.getUploadTime());
//            alarmInfo.setAlarmSourceType(ALARM_SOURCE_TYPE_RESOURCE);
//        }
//        return alarmInfo;
//    }

    /**
     * 运行系统资源信息上报
     * @param req
     * @return
     */
    @Override
    public Result systemResource(SystemResourceReq req) {
        Result result;
        try{
            List<SystemResourceVo> srList = req.getSrList();
            if (!CollectionUtils.isEmpty(srList)){
                for (SystemResourceVo vo : srList){
                    NmplSystemResource dto = new NmplSystemResource();
                    BeanUtils.copyProperties(vo,dto);
                    nmplSystemResourceMapper.insertSelective(dto);
                    putSystemResourceRedis(vo);
                }
            }
            result = buildResult(null);
        }catch (Exception e){
            log.error("MonitorServiceImpl.systemResource Exception:{}",e.getMessage());
            result = failResult(e);
        }
        return result;
    }

    /**
     * 物理设备网络拓扑图查询
     * @param req
     * @return
     */
    @Override
    public Result<QueryPhysicalDevicesResp> queryPhysicalDevices(QueryPhysicalDevicesReq req) {
        Result<QueryPhysicalDevicesResp> result;
        try{
            QueryPhysicalDevicesResp resp = new QueryPhysicalDevicesResp();
            checkPhysicalDevicesParam(req);
            String operatorId = req.getRelationOperatorId();
            Set<String> ips = distinctIps(operatorId);

            NmplPhysicalDeviceHeartbeatExample example = new NmplPhysicalDeviceHeartbeatExample();
            example.createCriteria().andUploadTimeEqualTo(DateUtils.getRecentHalfTime(new Date()));
            List<NmplPhysicalDeviceHeartbeat> heartbeats = nmplPhysicalDeviceHeartbeatMapper.selectByExample(example);
            List<PhysicalDeviceHeartbeatVo> heartbeatVos = new ArrayList<>();
            for (NmplPhysicalDeviceHeartbeat heartbeat : heartbeats){
                PhysicalDeviceHeartbeatVo vo = new PhysicalDeviceHeartbeatVo();
                String[] ipArray = heartbeat.getIp1Ip2().split(KEY_SPLIT_UNDERLINE);
                if (ips.contains(ipArray[0]) && ips.contains(ipArray[1])){
                    vo.setIp1(ipArray[0]);
                    vo.setIp2(ipArray[1]);
                    vo.setStatus(heartbeat.getStatus());
                    heartbeatVos.add(vo);
                }
            }

            resp.setIps(ips);
            resp.setVos(heartbeatVos);
            result = buildResult(resp);
        }catch (SystemException e){
            log.error("MonitorServiceImpl.queryPhysicalDevices SystemException:{}",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.error("MonitorServiceImpl.queryPhysicalDevices Exception:{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    /**
     * 物理设备资源查询
     * @param req
     * @return
     */
    @Override
    public Result<QueryPhysicalDeviceResourceResp> queryPhysicalDeviceResource(QueryPhysicalDevicesResourceReq req) {
        Result<QueryPhysicalDeviceResourceResp> result;
        try{
            QueryPhysicalDeviceResourceResp resp = new QueryPhysicalDeviceResourceResp();
            checkPhysicalDevicesResourceParam(req);

            NmplPhysicalDeviceResourceExample example = new NmplPhysicalDeviceResourceExample();
            example.createCriteria().andDeviceIpEqualTo(req.getDeviceIp());
            List<NmplPhysicalDeviceResource> resources = nmplPhysicalDeviceResourceMapper.selectByExample(example);
            List<PhysicalDeviceResourceVo> resourceVos = new ArrayList<>();
            for (NmplPhysicalDeviceResource resource : resources){
                PhysicalDeviceResourceVo vo = new PhysicalDeviceResourceVo();
                BeanUtils.copyProperties(resource,vo);
                resourceVos.add(vo);
            }
            resp.setResourceVos(resourceVos);
            result = buildResult(resp);
        }catch (SystemException e){
            log.error("MonitorServiceImpl.queryPhysicalDeviceResource SystemException:{}",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.error("MonitorServiceImpl.queryPhysicalDeviceResource Exception:{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    /**
     * 运行系统资源查询
     * @param req
     * @return
     */
    @Override
    public Result<QuerySystemResourceResp> querySystemResource(QueryPhysicalDevicesResourceReq req) {
        Result<QuerySystemResourceResp> result;
        try{
            QuerySystemResourceResp resp = new QuerySystemResourceResp();
            checkPhysicalDevicesResourceParam(req);
            Date now = new Date();
            List<SystemResourceVo> resourceVos = getSystemResources(req.getDeviceIp(),now);
            Map<String,List<String>> cpuInfos = getSystemResourceSL(resourceVos,RESOURCE_TYPE_CPU,DateUtils.getRecentHalfTime(now));
            Map<String,List<String>> memInfos = getSystemResourceSL(resourceVos,RESOURCE_TYPE_MEMORY,DateUtils.getRecentHalfTime(now));
            resp.setXTime(CommonServiceImpl.getXTimePerHalfHour(DateUtils.getRecentHalfTime(now), -24, 60 * 30, MINUTE_TIME_FORMAT));
            resp.setResourceVos(resourceVos);
            resp.setCpuInfos(cpuInfos);
            resp.setMemInfos(memInfos);
            result = buildResult(resp);
        }catch (SystemException e){
            log.error("MonitorServiceImpl.querySystemResource SystemException:{}",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.error("MonitorServiceImpl.querySystemResource Exception:{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    /**
     * 获取所有设备ip集合
     * @param operatorId
     * @return
     */
    private Set<String> distinctIps(String operatorId){
        Set<String> ips = new HashSet<>();
        NmplBaseStationInfoExample baseStationInfoExample = new NmplBaseStationInfoExample();
        baseStationInfoExample.createCriteria().andRelationOperatorIdEqualTo(operatorId).andIsExistEqualTo(IS_EXIST);
        List<NmplBaseStationInfo> stationInfos = nmplBaseStationInfoMapper.selectByExample(baseStationInfoExample);
        for (NmplBaseStationInfo  stationInfo : stationInfos){
            ips.add(stationInfo.getLanIp());
        }

        NmplDeviceInfoExample deviceInfoExample = new NmplDeviceInfoExample();
        deviceInfoExample.createCriteria().andRelationOperatorIdEqualTo(operatorId).andIsExistEqualTo(IS_EXIST);
        List<NmplDeviceInfo> deviceInfos = nmplDeviceInfoMapper.selectByExample(deviceInfoExample);
        for (NmplDeviceInfo deviceInfo : deviceInfos){
            ips.add(deviceInfo.getLanIp());
        }

        return ips;
    }

    /**
     * 插入运行系统信息缓存
     * @param vo
     */
    private void putSystemResourceRedis(SystemResourceVo vo){
        try{
            StringBuffer key = new StringBuffer(SYSTEM_NM);
            key.append(UNDERLINE).append(SYSTEM_RESOURCE).append(UNDERLINE).append(vo.getSystemId());
            String hashKey = DateUtils.formatDateToString2(vo.getUploadTime(),MINUTE_TIME_FORMAT);
            DisplayVo displayVo = new DisplayVo();
            displayVo.setDate(DateUtils.formatDateToInteger(vo.getUploadTime()));
            displayVo.setValue1(vo.getCpuPercent());
            displayVo.setValue2(vo.getMemoryPercent());
            redisTemplate.opsForHash().put(key.toString(),hashKey,displayVo);
        }catch (Exception e){
            log.warn("putSystemResourceRedis exception:{}",e);
        }
    }

    /**
     * 根据ip获取设备下所有系统的最新运行信息
     * @param deviceIp
     * @return
     */
    private List<SystemResourceVo> getSystemResources(String deviceIp,Date now){
        List<SystemResourceVo> voList = new ArrayList<>();
        Date date = DateUtils.getRecentHalfTime(now);
        NmplBaseStationInfoExample baseStationInfoExample = new NmplBaseStationInfoExample();
        baseStationInfoExample.createCriteria().andLanIpEqualTo(deviceIp).andIsExistEqualTo(IS_EXIST);
        List<NmplBaseStationInfo> baseStationInfos = nmplBaseStationInfoMapper.selectByExample(baseStationInfoExample);
        if (!CollectionUtils.isEmpty(baseStationInfos)){
            for (NmplBaseStationInfo info : baseStationInfos){
                SystemResourceVo vo = new SystemResourceVo();
                NmplSystemResourceExample example = new NmplSystemResourceExample();
                example.createCriteria().andUploadTimeEqualTo(date).andSystemIdEqualTo(info.getStationId());
                List<NmplSystemResource> resources = nmplSystemResourceMapper.selectByExample(example);
                if (!CollectionUtils.isEmpty(resources)){
                    BeanUtils.copyProperties(resources.get(0),vo);
                }else {
                    vo.setSystemId(info.getStationId());
                    vo.setCpuPercent("0");
                    vo.setMemoryPercent("0");
                    vo.setSystemType(info.getStationType());
                    vo.setRunTime(0L);
                    vo.setUploadTime(date);
                }
                voList.add(vo);
            }
        }

        NmplDeviceInfoExample deviceInfoExample = new NmplDeviceInfoExample();
        deviceInfoExample.createCriteria().andLanIpEqualTo(deviceIp).andIsExistEqualTo(IS_EXIST);
        List<NmplDeviceInfo> deviceInfos = nmplDeviceInfoMapper.selectByExample(deviceInfoExample);
        if (!CollectionUtils.isEmpty(deviceInfos)){
            for (NmplDeviceInfo info : deviceInfos){
                SystemResourceVo vo = new SystemResourceVo();
                NmplSystemResourceExample example = new NmplSystemResourceExample();
                example.createCriteria().andUploadTimeEqualTo(date).andSystemIdEqualTo(info.getDeviceId());
                List<NmplSystemResource> resources = nmplSystemResourceMapper.selectByExample(example);
                if (!CollectionUtils.isEmpty(resources)){
                    BeanUtils.copyProperties(resources.get(0),vo);
                }else {
                    vo.setSystemId(info.getDeviceId());
                    vo.setCpuPercent("0");
                    vo.setMemoryPercent("0");
                    vo.setSystemType(info.getDeviceType());
                    vo.setRunTime(0L);
                    vo.setUploadTime(date);
                }
                voList.add(vo);
            }
        }
        return voList;
    }

    private Map<String,List<String>> getSystemResourceSL(List<SystemResourceVo> vos,String resourceType,Date now){
        Map<String,List<String>> resMap = new HashMap<>();
        Date recentHalfTime = DateUtils.getRecentHalfTime(now);
        List<String> xTime = CommonServiceImpl.getXTimePerHalfHour(recentHalfTime, -24, 60 * 30, MINUTE_TIME_FORMAT);

        if (!CollectionUtils.isEmpty(vos)){
            String nowStr = DateUtils.formatDateToInteger(now);
            for (SystemResourceVo vo : vos){
                List<String> values = new ArrayList<>();
                String systemId = vo.getSystemId();
                StringBuffer hashKey = new StringBuffer(SYSTEM_NM);
                hashKey.append(UNDERLINE).append(SYSTEM_RESOURCE).append(UNDERLINE).append(systemId);
                Map<String,DisplayVo> entries = new HashMap<>();
                try{
                    entries = redisTemplate.opsForHash().entries(hashKey.toString());
                }catch (Exception e){
                    log.warn("getSystemResourceSL getall redis exception:{}",e);
                }

                if (entries.isEmpty()){
                    Date uploadTime = DateUtils.addDayForDate(recentHalfTime, -1);
                    NmplSystemResourceExample example = new NmplSystemResourceExample();
                    example.createCriteria().andUploadTimeGreaterThanOrEqualTo(uploadTime).andSystemIdEqualTo(systemId);
                    List<NmplSystemResource> resources = nmplSystemResourceMapper.selectByExample(example);
                    if (!CollectionUtils.isEmpty(resources)){
                        for (NmplSystemResource resource : resources){
                            DisplayVo displayVo = new DisplayVo();
                            displayVo.setDate(DateUtils.formatDateToInteger(resource.getUploadTime()));
                            displayVo.setValue1(resource.getCpuPercent());
                            displayVo.setValue2(resource.getMemoryPercent());
                            String mapKey = DateUtils.formatDateToString2(resource.getUploadTime(), MINUTE_TIME_FORMAT);
                            entries.put(mapKey,displayVo);
                        }
                        try {
                            redisTemplate.opsForHash().putAll(hashKey.toString(),entries);
                        }catch (Exception e){
                            log.warn("getSystemResourceSL putall redis exception:{}",e);
                        }
                    }

                }
                for (String time : xTime){
                    boolean isGetFromDb = true;
                    String value = DEFAULT_ZERO;
                    if (entries.containsKey(time)){
                        DisplayVo displayVo = entries.get(time);
                        if (nowStr.equals(displayVo.getDate())){
                            if (RESOURCE_TYPE_CPU.equals(resourceType)){
                                value = displayVo.getValue1();
                            }else if (RESOURCE_TYPE_MEMORY.equals(resourceType)){
                                value = displayVo.getValue2();
                            }
                            isGetFromDb = false;
                        }
                    }
                    if (isGetFromDb){
                        Date uploadTime = CommonServiceImpl.getDateByStr(nowStr + time);
                        NmplSystemResourceExample example = new NmplSystemResourceExample();
                        example.createCriteria().andSystemIdEqualTo(systemId).andSystemTypeEqualTo(vo.getSystemType()).andUploadTimeEqualTo(uploadTime);
                        List<NmplSystemResource> resources = nmplSystemResourceMapper.selectByExample(example);
                        SystemResourceVo resourceVo = new SystemResourceVo();
                        if (!CollectionUtils.isEmpty(resources)){
                            BeanUtils.copyProperties(resources.get(0),resourceVo);
                            if (RESOURCE_TYPE_CPU.equals(resourceType)){
                                value = resources.get(0).getCpuPercent();
                            }else if (RESOURCE_TYPE_MEMORY.equals(resourceType)){
                                value = resources.get(0).getMemoryPercent();
                            }
                            putSystemResourceRedis(resourceVo);
                        }else {
                            resourceVo.setSystemId(systemId);
                            resourceVo.setUploadTime(uploadTime);
                            resourceVo.setCpuPercent(DEFAULT_ZERO);
                            resourceVo.setMemoryPercent(DEFAULT_ZERO);
                            putSystemResourceRedis(resourceVo);
                        }
                    }
                    values.add(value);
                }
                resMap.put(systemId,values);
            }
        }
        return resMap;
    }


//    @Override
//    public Result<QueryMonitorResp> queryMonitor(QueryMonitorReq req) {
//        Result result;
//
//        try {
//            QueryMonitorResp resp = new QueryMonitorResp();
//            queryMonitorParam(req);
//            String roId = req.getRoId();
//            NmplBaseStationInfoExample baseStationInfoExample = new NmplBaseStationInfoExample();
//            baseStationInfoExample.createCriteria().andIsExistEqualTo(IS_EXIST).andRelationOperatorIdEqualTo(roId);
//            List<NmplBaseStationInfo> baseStationInfos = nmplBaseStationInfoMapper.selectByExample(baseStationInfoExample);
//
//            NmplDeviceInfoExample deviceInfoExample = new NmplDeviceInfoExample();
//            deviceInfoExample.createCriteria().andIsExistEqualTo(IS_EXIST).andRelationOperatorIdEqualTo(roId);
//            List<NmplDeviceInfo> deviceInfos = nmplDeviceInfoMapper.selectByExample(deviceInfoExample);
//
//            Map<Integer, List<DeviceInfoRelVo>> deviceInfoMap = new HashMap<>();
//            Long userCount = 0L;
//            Long totalBandwidth = 0L;
//
//            if (!CollectionUtils.isEmpty(baseStationInfos)){
//                List<DeviceInfoRelVo> levelThree = new ArrayList<>();
//                for (NmplBaseStationInfo baseStationInfo : baseStationInfos){
//                    DeviceInfoRelVo baseInfo = new DeviceInfoRelVo();
//                    BeanUtils.copyProperties(baseStationInfo,baseInfo);
//                    baseInfo.setDeviceId(baseStationInfo.getStationId());
//                    baseInfo.setDeviceName(baseStationInfo.getStationName());
//                    baseInfo.setDeviceType(CONFIG_DEVICE_TYPE_1);
//                    levelThree.add(baseInfo);
//                    userCount = addUserCount(userCount, baseStationInfo.getStationId());
//                    totalBandwidth = addTotalBandwidth(totalBandwidth, baseStationInfo.getStationId());
//                }
//                deviceInfoMap.put(LEVEL_3,levelThree);
//            }
//
//            if (!CollectionUtils.isEmpty(deviceInfos)){
//                List<DeviceInfoRelVo> levelOne = new ArrayList<>();
//                List<DeviceInfoRelVo> levelTwo = new ArrayList<>();
//                for (NmplDeviceInfo deviceInfo : deviceInfos){
//                    DeviceInfoRelVo deviceInfoRelVo = new DeviceInfoRelVo();
//                    BeanUtils.copyProperties(deviceInfo,deviceInfoRelVo);
//
//                    switch (deviceInfo.getDeviceType()){
//                        case SYSTEM_ID_1:
//                            deviceInfoRelVo.setDeviceType(CONFIG_DEVICE_TYPE_2);
//                            levelTwo.add(deviceInfoRelVo);
//                            break;
//                        case SYSTEM_ID_2:
//                            deviceInfoRelVo.setDeviceType(CONFIG_DEVICE_TYPE_3);
//                            levelOne.add(deviceInfoRelVo);
//                            break;
//                        case SYSTEM_ID_3:
//                            deviceInfoRelVo.setDeviceType(CONFIG_DEVICE_TYPE_4);
//                            levelTwo.add(deviceInfoRelVo);
//                            break;
//                        default:
//                            deviceInfoRelVo.setDeviceType(null);
//                            break;
//                    }
//                    userCount = addUserCount(userCount, deviceInfo.getDeviceId());
//                    totalBandwidth = addTotalBandwidth(totalBandwidth, deviceInfo.getDeviceId());
//                }
//                deviceInfoMap.put(LEVEL_1,levelOne);
//                deviceInfoMap.put(LEVEL_2,levelTwo);
//            }
//
//
//            if (!CollectionUtils.isEmpty(deviceInfoMap)){
//                for (Map.Entry<Integer, List<DeviceInfoRelVo>> entry : deviceInfoMap.entrySet()){
//                    List<DeviceInfoRelVo> baseInfos = entry.getValue();
//                    if (!CollectionUtils.isEmpty(baseInfos)){
//                        // 找同级设备
//                        for (DeviceInfoRelVo baseInfo : baseInfos){
//                            List<NmplDeviceInfo> sameLevelInfo = nmplDeviceExtMapper.selectBaseStationListByMainDeviceId(baseInfo.getDeviceId(), roId);
//                            if (!CollectionUtils.isEmpty(sameLevelInfo)){
//                                List<DeviceInfoRelVo> sameLevelInfos = new ArrayList<>(sameLevelInfo.size());
//                                for (NmplDeviceInfo deviceInfo : sameLevelInfo){
//                                    DeviceInfoRelVo deviceInfoRelVo = new DeviceInfoRelVo();
//                                    BeanUtils.copyProperties(deviceInfo,deviceInfoRelVo);
//                                    sameLevelInfos.add(deviceInfoRelVo);
//                                }
//                                baseInfo.setRelDevices(sameLevelInfos);
//                            }
//                        }
//
//                        // 找子级设备
//                        for (DeviceInfoRelVo baseInfo : baseInfos){
//                            List<NmplDeviceInfo> childLevelInfo = nmplDeviceExtMapper.selectDeviceListByMainDeviceId(baseInfo.getDeviceId(), roId);
//                            if (!CollectionUtils.isEmpty(childLevelInfo)){
//                                List<DeviceInfoRelVo> childLevelInfos = new ArrayList<>(childLevelInfo.size());
//                                for (NmplDeviceInfo deviceInfo : childLevelInfo){
//                                    DeviceInfoRelVo deviceInfoRelVo = new DeviceInfoRelVo();
//                                    BeanUtils.copyProperties(deviceInfo,deviceInfoRelVo);
//                                    childLevelInfos.add(deviceInfoRelVo);
//                                }
//                                baseInfo.setChildrenDevices(childLevelInfos);
//                            }
//                        }
//                    }
//                }
//            }
//
//            resp.setDeviceInfoMap(deviceInfoMap);
//            result = buildResult(resp);
//        }catch (Exception e){
//            log.error("MonitorServiceImpl.queryMonitor Exception:{}",e.getMessage());
//            result = failResult(e);
//        }
//        return result;
//    }
//
//    @Override
//    public Result<TotalLoadChangeResp> totalLoadChange(TotalLoadChangeReq req) {
//        Result result;
//
//        try {
//            TotalLoadChangeResp resp = null;
//            totalLoadChangeParam(req);
//
//            switch (req.getBigType()){
//                case DEVICE_BIG_TYPE_0:
//                    NmplBaseStationInfoExample bexample = new NmplBaseStationInfoExample();
//                    bexample.createCriteria().andStationTypeEqualTo(req.getDeviceType()).andRelationOperatorIdEqualTo(req.getRoId()).andIsExistEqualTo(IS_EXIST);
//                    List<NmplBaseStationInfo> stationInfos = nmplBaseStationInfoMapper.selectByExample(bexample);
//                    if (!CollectionUtils.isEmpty(stationInfos)){
//                        Map<String, List> dataMap = new HashMap<>();
//                        List<Long> inTotalLoadVos = new ArrayList<>(24*60/15);
//                        List<Long> outTotalLoadVos = new ArrayList<>(24*60/15);
//                        List<String> timeString = new ArrayList<>(24*60/15);
//
//                        Date startTime = DateUtils.getStartForDay(new Date());
//                        for (int i=0; i<24*60/SPLIT_TIME ; i++){
//                            BigDecimal inTotalLoad = BigDecimal.ZERO;
//                            BigDecimal outTotalLoad = BigDecimal.ZERO;
//                            Date endTime = DateUtils.addMinuteForDate(startTime, SPLIT_TIME);
//                            for (NmplBaseStationInfo stationInfo : stationInfos){
//                                // 查询设备的内网总带宽
//                                BigDecimal decimal1 = nmplDataCollectExtMapper.countLoad(stationInfo.getStationId(), INTRANET_BROADBAND_LOAD_CODE, startTime, endTime);
//                                if (decimal1 != null){
//                                    inTotalLoad = inTotalLoad.add(decimal1);
//                                }
//
//                                // 查询设备的外网总带宽
//                                BigDecimal decimal2 = nmplDataCollectExtMapper.countLoad(stationInfo.getStationId(), INTERNET_BROADBAND_LOAD_CODE, startTime, endTime);
//                                if (decimal2 != null){
//                                    outTotalLoad = outTotalLoad.add(decimal2);
//                                }
//                            }
//                            inTotalLoadVos.add(inTotalLoad.longValueExact());
//                            outTotalLoadVos.add(outTotalLoad.longValueExact());
//                            timeString.add(DateUtils.dateToString(endTime, DateUtils.MINUTE_TIME_FORMAT));
//                            startTime = endTime;
//                        }
//                        dataMap.put(INTRANET_BROADBAND_LOAD_CODE,inTotalLoadVos);
//                        dataMap.put(INTERNET_BROADBAND_LOAD_CODE,outTotalLoadVos);
//                        dataMap.put(TIMER_SHAFT,timeString);
//                        resp = new TotalLoadChangeResp();
//                        resp.setDataMap(dataMap);
//                    }
//                    break;
//                case DEVICE_BIG_TYPE_1:
//                    NmplDeviceInfoExample dexample = new NmplDeviceInfoExample();
//                    dexample.createCriteria().andDeviceTypeEqualTo(req.getDeviceType()).andRelationOperatorIdEqualTo(req.getRoId()).andIsExistEqualTo(IS_EXIST);
//                    List<NmplDeviceInfo> deviceInfos = nmplDeviceInfoMapper.selectByExample(dexample);
//                    if (!CollectionUtils.isEmpty(deviceInfos)){
//                        Map<String, List> dataMap = new HashMap<>();
//                        List<Long> inTotalLoadVos = new ArrayList<>(24*60/15);
//                        List<Long> outTotalLoadVos = new ArrayList<>(24*60/15);
//                        List<String> timeString = new ArrayList<>(24*60/15);
//
//                        Date startTime = DateUtils.getStartForDay(new Date());
//                        for (int i=0; i<24*60/SPLIT_TIME; i++){
//                            BigDecimal inTotalLoad = BigDecimal.ZERO;
//                            BigDecimal outTotalLoad = BigDecimal.ZERO;
//                            Date endTime = DateUtils.addMinuteForDate(startTime, SPLIT_TIME);
//                            for (NmplDeviceInfo deviceInfo : deviceInfos){
//                                // 查询设备的内网总带宽
//                                BigDecimal decimal1 = nmplDataCollectExtMapper.countLoad(deviceInfo.getDeviceId(), INTRANET_BROADBAND_LOAD_CODE, startTime, endTime);
//                                if (decimal1 != null){
//                                    inTotalLoad = inTotalLoad.add(decimal1);
//                                }
//
//                                // 查询设备的外网总带宽
//                                BigDecimal decimal2 = nmplDataCollectExtMapper.countLoad(deviceInfo.getDeviceId(), INTERNET_BROADBAND_LOAD_CODE, startTime, endTime);
//                                if (decimal2 != null){
//                                    outTotalLoad = outTotalLoad.add(decimal2);
//                                }
//                            }
//                            inTotalLoadVos.add(inTotalLoad.longValueExact());
//                            outTotalLoadVos.add(outTotalLoad.longValueExact());
//                            timeString.add(DateUtils.dateToString(endTime, DateUtils.MINUTE_TIME_FORMAT));
//                            startTime = endTime;
//                        }
//                        dataMap.put(INTRANET_BROADBAND_LOAD_CODE,inTotalLoadVos);
//                        dataMap.put(INTERNET_BROADBAND_LOAD_CODE,outTotalLoadVos);
//                        dataMap.put(TIMER_SHAFT,timeString);
//                        resp = new TotalLoadChangeResp();
//                        resp.setDataMap(dataMap);
//                    }
//                    break;
//                default:
//                    log.error("MonitorServiceImpl.totalLoadChange error: 设备大类{}",req.getBigType());
//                    throw new SystemException(ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG);
//            }
//            result = buildResult(resp);
//        }catch (Exception e){
//            log.error("MonitorServiceImpl.totalLoadChange Exception:{}",e.getMessage());
//            result = failResult(e);
//        }
//
//        return result;
//    }


    /**
     * 1：在线 2：未激活 3：下线 4:其他
     * @param deviceId
     * @return
     *
     */
    private Map<String,String> checkStationStatus(String deviceId){
        Map<String,String> resultMap = new HashMap<>(2);

        // 先判断设备类型 获取设备id 信息
        NmplBaseStationInfoExample example = new NmplBaseStationInfoExample();
        example.createCriteria().andStationIdEqualTo(deviceId).andIsExistEqualTo(IS_EXIST);
        List<NmplBaseStationInfo> stationInfos = nmplBaseStationInfoMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(stationInfos)){
            NmplDeviceInfoExample dExample = new NmplDeviceInfoExample();
            dExample.createCriteria().andDeviceIdEqualTo(deviceId).andIsExistEqualTo(IS_EXIST);
            List<NmplDeviceInfo> deviceInfos = nmplDeviceInfoMapper.selectByExample(dExample);
            if (CollectionUtils.isEmpty(deviceInfos)){
                throw new SystemException(DEVICE_NOT_EXIST_MSG);
            }else {
                NmplDeviceInfo deviceInfo = deviceInfos.get(0);
                resultMap.put("status",deviceInfo.getStationStatus());
                resultMap.put("bigType",DEVICE_BIG_TYPE_1);
                resultMap.put("id",String.valueOf(deviceInfo.getId()));
            }
        }else {
            NmplBaseStationInfo baseStationInfo = stationInfos.get(0);
            resultMap.put("status",baseStationInfo.getStationStatus());
            resultMap.put("bigType",DEVICE_BIG_TYPE_0);
            resultMap.put("id",String.valueOf(baseStationInfo.getId()));
        }

//        // 再查看缓存是否在线
//        try {
//            Object key = redisTemplate.opsForValue().get(HEART_CHECK_DEVICE_ID+deviceId);
//
//            if (key != null){
//                resultMap.put("status",STATION_STATUS_ACTIVE);
//                return resultMap;
//            }
//        }catch (Exception e){
//            log.warn("get heart_check_device_id error,id:{},msg:{}",deviceId,e.getMessage());
//        }

        return resultMap;
    }

//    private Long addUserCount(Long userCount,String deviceId){
//        NmplDataCollectExample example = new NmplDataCollectExample();
//        example.setOrderByClause("upload_time desc");
//        example.createCriteria().andDeviceIdEqualTo(deviceId).andDataItemCodeEqualTo(USER_COUNT_CODE).andUploadTimeGreaterThan(DateUtils.addMinuteForDate(new Date(),-USER_COUNT_TIME));
//        List<NmplDataCollect> dataCollects = nmplDataCollectMapper.selectByExample(example);
//        if (!CollectionUtils.isEmpty(dataCollects)){
//            String dataItemValue = dataCollects.get(0).getDataItemValue();
//            userCount = userCount + Long.valueOf(dataItemValue);
//        }
//        return userCount;
//    }
//
//    private Long addTotalBandwidth(Long totalBandwidth,String deviceId){
//        NmplDataCollectExample example = new NmplDataCollectExample();
//        example.setOrderByClause("upload_time desc");
//        example.createCriteria().andDeviceIdEqualTo(deviceId).andDataItemCodeEqualTo(TOTAL_BAND_WIDTH_CODE).andUploadTimeGreaterThan(DateUtils.addMinuteForDate(new Date(),-TOTAL_BAND_WIDTH_TIME));
//        List<NmplDataCollect> dataCollects = nmplDataCollectMapper.selectByExample(example);
//        if (!CollectionUtils.isEmpty(dataCollects)){
//            String dataItemValue = dataCollects.get(0).getDataItemValue();
//            totalBandwidth = totalBandwidth + Long.valueOf(dataItemValue);
//        }
//        return totalBandwidth;
//    }

    private void checkHeartParam(CheckHeartReq req) {
        if (ParamCheckUtil.checkVoStrBlank(req.getDeviceId())){
            throw new SystemException(ErrorCode.PARAM_IS_NULL, "deviceId"+ ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }

//    private void queryMonitorParam(QueryMonitorReq req) {
//        if (ParamCheckUtil.checkVoStrBlank(req.getRoId())){
//            throw new SystemException(ErrorCode.PARAM_IS_NULL, "roId"+ ErrorMessageContants.PARAM_IS_NULL_MSG);
//        }
//    }
//
//    private void totalLoadChangeParam(TotalLoadChangeReq req) {
//        if (ParamCheckUtil.checkVoStrBlank(req.getRoId())){
//            throw new SystemException(ErrorCode.PARAM_IS_NULL, "roId"+ ErrorMessageContants.PARAM_IS_NULL_MSG);
//        }
//        if (ParamCheckUtil.checkVoStrBlank(req.getBigType())){
//            throw new SystemException(ErrorCode.PARAM_IS_NULL, "bigType"+ ErrorMessageContants.PARAM_IS_NULL_MSG);
//        }
//        if (ParamCheckUtil.checkVoStrBlank(req.getDeviceType())){
//            throw new SystemException(ErrorCode.PARAM_IS_NULL, "deviceType"+ ErrorMessageContants.PARAM_IS_NULL_MSG);
//        }
//    }


    private void heartHttpPush(String bigType,String id,String deviceId,String latestStatus) throws Exception {
        if (DEVICE_BIG_TYPE_0.equals(bigType)){
            NmplBaseStationInfo baseStationInfo = new NmplBaseStationInfo();
            baseStationInfo.setId(Long.parseLong(id));
            baseStationInfo.setStationStatus(latestStatus);
            int num =nmplBaseStationInfoMapper.updateByPrimaryKeySelective(baseStationInfo);
            if(num==1){
                baseStationInfoService.pushToProxy(deviceId, URL_STATION_UPDATE);
            }
        }else if (DEVICE_BIG_TYPE_1.equals(bigType)){
            NmplDeviceInfo deviceInfo = new NmplDeviceInfo();
            deviceInfo.setId(Long.parseLong(id));
            deviceInfo.setStationStatus(latestStatus);
            int num =nmplDeviceInfoMapper.updateByPrimaryKeySelective(deviceInfo);
            if(num==1){
                deviceService.pushToProxy(deviceId,URL_DEVICE_UPDATE);
            }
        }
    }

    private void checkPhysicalDevicesParam(QueryPhysicalDevicesReq req) {
        if (ParamCheckUtil.checkVoStrBlank(req.getRelationOperatorId())){
            throw new SystemException(ErrorCode.PARAM_IS_NULL, "relationOperatorId"+ ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }

    private void checkPhysicalDevicesResourceParam(QueryPhysicalDevicesResourceReq req) {
        if (ParamCheckUtil.checkVoStrBlank(req.getDeviceIp())){
            throw new SystemException(ErrorCode.PARAM_IS_NULL, "deviceIp"+ ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }


}
