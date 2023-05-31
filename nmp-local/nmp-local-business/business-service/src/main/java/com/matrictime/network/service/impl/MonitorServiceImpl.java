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


    /**
     * 站点状态监测
     * @param req
     * @return
     */
    @Override
    public Result<CheckHeartResp> checkHeart(CheckHeartReq req) {
        Result result;
        CheckHeartResp resp = null;
        try {
            // 校验入参
            checkHeartParam(req);
            Map<String, String> map = checkStationStatus(req.getDeviceId());
            // 插入缓存
            redisTemplate.opsForValue().set(HEART_CHECK_DEVICE_ID+req.getDeviceId(),req.getStatus(),healthDeadlineTime, TimeUnit.SECONDS);
            String status = map.get("status");
            String bigType = map.get("bigType");
            String id = map.get("id");
            // 判断当前状态与上报状态是否一致
            if(!deviceMap.get(req.getStatus()).equals(status)){
                // 不一致进行中心数据更新并推送至各个代理
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
                // 获取物理设备ip间心跳信息，有则更新，无则插入
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
            for (PhysicalDeviceResourceVo vo : pdrList){
                NmplPhysicalDeviceResource dto = new NmplPhysicalDeviceResource();
                BeanUtils.copyProperties(vo,dto);
                // 获取物理设备资源信息，有则更新，无则插入
                NmplPhysicalDeviceResource resource = nmplPhysicalDeviceResourceMapper.selectByPrimaryKey(vo.getDeviceIp(), vo.getResourceType());
                if (resource != null){
                    nmplPhysicalDeviceResourceMapper.updateByPrimaryKeySelective(dto);
                }else {
                    nmplPhysicalDeviceResourceMapper.insertSelective(dto);
                }
            }
            result = buildResult(null);
        }catch (Exception e){
            log.error("MonitorServiceImpl.physicalDeviceResource Exception:{}",e.getMessage());
            result = failResult(e);
        }
        return result;
    }


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
                    // 插入数据库
                    nmplSystemResourceMapper.insertSelective(dto);
                    // 插入缓存
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
            // 校验请求入参
            checkPhysicalDevicesParam(req);
            String operatorId = req.getRelationOperatorId();
            // 获取所有设备ip集合
            Set<String> ips = distinctIps(operatorId);

            NmplPhysicalDeviceHeartbeatExample example = new NmplPhysicalDeviceHeartbeatExample();
            example.createCriteria().andUploadTimeEqualTo(DateUtils.getRecentHalfTime(new Date()));
            // 获取最近一个上报时间点的ip间心跳上报情况
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
            // 校验请求入参
            checkPhysicalDevicesResourceParam(req);

            NmplPhysicalDeviceResourceExample example = new NmplPhysicalDeviceResourceExample();
            example.createCriteria().andDeviceIpEqualTo(req.getDeviceIp());
            // 根据设备ip获取该设备的物理资源信息
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
            // 校验请求入参
            checkPhysicalDevicesResourceParam(req);
            Date now = new Date();

            // 根据ip获取设备下所有系统的最新运行信息
            List<SystemResourceVo> resourceVos = getSystemResources(req.getDeviceIp(),now);
            // 获取系统列表最近12个小时的cpu上报折线图信息
            Map<String,List<String>> cpuInfos = getSystemResourceSL(resourceVos,RESOURCE_TYPE_CPU,DateUtils.getRecentHalfTime(now));
            // 获取系统列表最近12个小时的内存上报折线图信息
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
        // 根据内网ip获取所有基站信息
        List<NmplBaseStationInfo> baseStationInfos = nmplBaseStationInfoMapper.selectByExample(baseStationInfoExample);
        if (!CollectionUtils.isEmpty(baseStationInfos)){
            for (NmplBaseStationInfo info : baseStationInfos){
                SystemResourceVo vo = new SystemResourceVo();
                NmplSystemResourceExample example = new NmplSystemResourceExample();
                example.createCriteria().andUploadTimeEqualTo(date).andSystemIdEqualTo(info.getStationId());
                // 根据站点id获取最近上报点的资源信息
                List<NmplSystemResource> resources = nmplSystemResourceMapper.selectByExample(example);
                if (!CollectionUtils.isEmpty(resources)){
                    BeanUtils.copyProperties(resources.get(0),vo);
                }else {
                    vo.setSystemId(info.getStationId());
                    vo.setCpuPercent(DEFAULT_STR_ZERO);
                    vo.setMemoryPercent(DEFAULT_STR_ZERO);
                    vo.setSystemType(info.getStationType());
                    vo.setRunTime(Long.parseLong(DEFAULT_STR_ZERO));
                    vo.setUploadTime(date);
                }
                voList.add(vo);
            }
        }

        NmplDeviceInfoExample deviceInfoExample = new NmplDeviceInfoExample();
        deviceInfoExample.createCriteria().andLanIpEqualTo(deviceIp).andIsExistEqualTo(IS_EXIST);
        // 根据内网ip获取所有设备信息
        List<NmplDeviceInfo> deviceInfos = nmplDeviceInfoMapper.selectByExample(deviceInfoExample);
        if (!CollectionUtils.isEmpty(deviceInfos)){
            for (NmplDeviceInfo info : deviceInfos){
                SystemResourceVo vo = new SystemResourceVo();
                NmplSystemResourceExample example = new NmplSystemResourceExample();
                example.createCriteria().andUploadTimeEqualTo(date).andSystemIdEqualTo(info.getDeviceId());
                // 根据站点id获取最近上报点的资源信息
                List<NmplSystemResource> resources = nmplSystemResourceMapper.selectByExample(example);
                if (!CollectionUtils.isEmpty(resources)){
                    BeanUtils.copyProperties(resources.get(0),vo);
                }else {
                    vo.setSystemId(info.getDeviceId());
                    vo.setCpuPercent(DEFAULT_STR_ZERO);
                    vo.setMemoryPercent(DEFAULT_STR_ZERO);
                    vo.setSystemType(info.getDeviceType());
                    vo.setRunTime(Long.parseLong(DEFAULT_STR_ZERO));
                    vo.setUploadTime(date);
                }
                voList.add(vo);
            }
        }
        return voList;
    }

    /**
     * 获取系统资源信息折线图的上报信息
     * @param vos
     * @param resourceType
     * @param now
     * @return
     */
    private Map<String,List<String>> getSystemResourceSL(List<SystemResourceVo> vos,String resourceType,Date now){
        Map<String,List<String>> resMap = new HashMap<>();
        Date recentHalfTime = DateUtils.getRecentHalfTime(now);
        // 获取当前查询时间最近12小时的上报时间点
        List<String> xTime = CommonServiceImpl.getXTimePerHalfHour(recentHalfTime, -24, 60 * 30, MINUTE_TIME_FORMAT);

        if (!CollectionUtils.isEmpty(vos)){
            String nowStr = DateUtils.formatDateToInteger(now);
            // 遍历系统获取每一个系统的资源信息折线图
            for (SystemResourceVo vo : vos){
                List<String> values = new ArrayList<>();
                String systemId = vo.getSystemId();
                StringBuffer hashKey = new StringBuffer(SYSTEM_NM);
                hashKey.append(UNDERLINE).append(SYSTEM_RESOURCE).append(UNDERLINE).append(systemId);
                Map<String,DisplayVo> entries = new HashMap<>();
                try{
                    // 先从缓存获取
                    entries = redisTemplate.opsForHash().entries(hashKey.toString());
                }catch (Exception e){
                    log.warn("getSystemResourceSL getall redis exception:{}",e);
                }

                // 缓存为空从数据库获取
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
                            // 数据库获取结果插入缓存
                            redisTemplate.opsForHash().putAll(hashKey.toString(),entries);
                        }catch (Exception e){
                            log.warn("getSystemResourceSL putall redis exception:{}",e);
                        }
                    }

                }
                // 将查询出的数据与时间折线节点进行匹配（处理可能时间节点没有查询到上报数据的情况）
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
                    // 有时间节点缺数据
                    if (isGetFromDb){
                        Date uploadTime = CommonServiceImpl.getDateByStr(nowStr + time);
                        NmplSystemResourceExample example = new NmplSystemResourceExample();
                        example.createCriteria().andSystemIdEqualTo(systemId).andSystemTypeEqualTo(vo.getSystemType()).andUploadTimeEqualTo(uploadTime);
                        // 查询数据库
                        List<NmplSystemResource> resources = nmplSystemResourceMapper.selectByExample(example);
                        SystemResourceVo resourceVo = new SystemResourceVo();
                        if (!CollectionUtils.isEmpty(resources)){
                            BeanUtils.copyProperties(resources.get(0),resourceVo);
                            if (RESOURCE_TYPE_CPU.equals(resourceType)){
                                value = resources.get(0).getCpuPercent();
                            }else if (RESOURCE_TYPE_MEMORY.equals(resourceType)){
                                value = resources.get(0).getMemoryPercent();
                            }
                            // 有数据插入缓存
                            putSystemResourceRedis(resourceVo);
                        }else {
                            resourceVo.setSystemId(systemId);
                            resourceVo.setUploadTime(uploadTime);
                            resourceVo.setCpuPercent(DEFAULT_ZERO);
                            resourceVo.setMemoryPercent(DEFAULT_ZERO);
                            // 没有数据插入默认值到缓存
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


    /**
     * 获取当前站点状态 01:待激活  02:在线  03:外网异常 04:下线'
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

        return resultMap;
    }

    private void checkHeartParam(CheckHeartReq req) {
        if (ParamCheckUtil.checkVoStrBlank(req.getDeviceId())){
            throw new SystemException(ErrorCode.PARAM_IS_NULL, "deviceId"+ ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (ParamCheckUtil.checkVoStrBlank(req.getStatus())){
            throw new SystemException(ErrorCode.PARAM_IS_NULL, "status"+ ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }


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
