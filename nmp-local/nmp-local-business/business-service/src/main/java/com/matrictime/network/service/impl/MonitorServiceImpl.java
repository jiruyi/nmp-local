package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.SystemException;
import com.matrictime.network.constant.DataConstants;
import com.matrictime.network.dao.mapper.NmplBaseStationInfoMapper;
import com.matrictime.network.dao.mapper.NmplDataCollectMapper;
import com.matrictime.network.dao.mapper.NmplDeviceInfoMapper;
import com.matrictime.network.dao.mapper.extend.NmplDataCollectExtMapper;
import com.matrictime.network.dao.mapper.extend.NmplDeviceExtMapper;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.exception.ErrorCode;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.DeviceInfoRelVo;
import com.matrictime.network.modelVo.TotalLoadVo;
import com.matrictime.network.request.CheckHeartReq;
import com.matrictime.network.request.QueryMonitorReq;
import com.matrictime.network.request.TotalLoadChangeReq;
import com.matrictime.network.response.CheckHeartResp;
import com.matrictime.network.response.QueryMonitorResp;
import com.matrictime.network.response.TotalLoadChangeResp;
import com.matrictime.network.service.MonitorService;
import com.matrictime.network.util.DateUtils;
import com.matrictime.network.util.ParamCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.matrictime.network.base.constant.DataConstants.*;
import static com.matrictime.network.base.exception.ErrorMessageContants.DEVICE_NOT_EXIST_MSG;

@Slf4j
@Service
@PropertySource(value = "classpath:/businessConfig.properties",encoding = "UTF-8")
public class MonitorServiceImpl extends SystemBaseService implements MonitorService {

    @Value("${health.deadline.time}")
    private Long healthDeadlineTime;

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

    private static final String USER_COUNT_CODE = "userNumber";
    private static final String TOTAL_BAND_WIDTH_CODE = "bandwidth";

    // TODO: 2022/6/27 上线前需要确认配置信息
    private static final int USER_COUNT_TIME = 15;
    private static final int TOTAL_BAND_WIDTH_TIME = 15;
    private static final int SPLIT_TIME = 15;

    @Override
    public Result<CheckHeartResp> checkHeart(CheckHeartReq req) {
        Result result;
        CheckHeartResp resp = null;
        try {
            checkHeartParam(req);

            Map<String, String> map = checkStationStatus(req.getDeviceId());
            String status = map.get("status");

            switch (status){
                case STATION_STATUS_UNACTIVE:
                case STATION_STATUS_DOWN:
                    // 未激活和下线更新表状态
                    String bigType = map.get("bigType");
                    String id = map.get("id");
                    if (DEVICE_BIG_TYPE_0.equals(bigType)){
                        NmplBaseStationInfo baseStationInfo = new NmplBaseStationInfo();
                        baseStationInfo.setId(Long.parseLong(id));
                        baseStationInfo.setStationStatus(STATION_STATUS_ACTIVE);
                        nmplBaseStationInfoMapper.updateByPrimaryKeySelective(baseStationInfo);
                    }else if (DEVICE_BIG_TYPE_1.equals(bigType)){
                        NmplDeviceInfo deviceInfo = new NmplDeviceInfo();
                        deviceInfo.setId(Long.parseLong(id));
                        deviceInfo.setStationStatus(STATION_STATUS_ACTIVE);
                        nmplDeviceInfoMapper.updateByPrimaryKeySelective(deviceInfo);
                    }
                case STATION_STATUS_ACTIVE:
                    // 激活更新缓存时间
                    redisTemplate.opsForValue().set(HEART_CHECK_DEVICE_ID+req.getDeviceId(),true,healthDeadlineTime, TimeUnit.SECONDS);
                    break;
                default:
                    log.error("MonitorServiceImpl.checkHeart error: 设备id:{},状态为{}",req.getDeviceId(),status);
                    throw new SystemException(ErrorMessageContants.DEVICE_NOT_ACTIVE_MSG);
            }

            result = buildResult(resp);
        }catch (Exception e){
            log.error("MonitorServiceImpl.checkHeart Exception:{}",e.getMessage());
            result = failResult(e);
        }

        return result;
    }

    @Override
    public Result<QueryMonitorResp> queryMonitor(QueryMonitorReq req) {
        Result result;

        try {
            QueryMonitorResp resp = new QueryMonitorResp();
            queryMonitorParam(req);
            String roId = req.getRoId();
            NmplBaseStationInfoExample baseStationInfoExample = new NmplBaseStationInfoExample();
            baseStationInfoExample.createCriteria().andIsExistEqualTo(DataConstants.IS_EXIST).andRelationOperatorIdEqualTo(roId);
            List<NmplBaseStationInfo> baseStationInfos = nmplBaseStationInfoMapper.selectByExample(baseStationInfoExample);

            NmplDeviceInfoExample deviceInfoExample = new NmplDeviceInfoExample();
            deviceInfoExample.createCriteria().andIsExistEqualTo(DataConstants.IS_EXIST).andRelationOperatorIdEqualTo(roId);
            List<NmplDeviceInfo> deviceInfos = nmplDeviceInfoMapper.selectByExample(deviceInfoExample);

            Map<Integer, List<DeviceInfoRelVo>> deviceInfoMap = new HashMap<>();
            Long userCount = 0L;
            Long totalBandwidth = 0L;

            if (!CollectionUtils.isEmpty(baseStationInfos)){
                List<DeviceInfoRelVo> levelThree = new ArrayList<>();
                for (NmplBaseStationInfo baseStationInfo : baseStationInfos){
                    DeviceInfoRelVo baseInfo = new DeviceInfoRelVo();
                    BeanUtils.copyProperties(baseStationInfo,baseInfo);
                    baseInfo.setDeviceId(baseStationInfo.getStationId());
                    baseInfo.setDeviceName(baseStationInfo.getStationName());
                    baseInfo.setDeviceType(CONFIG_DEVICE_TYPE_1);
                    levelThree.add(baseInfo);
                    userCount = addUserCount(userCount, baseStationInfo.getStationId());
                    totalBandwidth = addTotalBandwidth(totalBandwidth, baseStationInfo.getStationId());
                }
                deviceInfoMap.put(LEVEL_3,levelThree);
            }

            if (!CollectionUtils.isEmpty(deviceInfos)){
                List<DeviceInfoRelVo> levelOne = new ArrayList<>();
                List<DeviceInfoRelVo> levelTwo = new ArrayList<>();
                for (NmplDeviceInfo deviceInfo : deviceInfos){
                    DeviceInfoRelVo deviceInfoRelVo = new DeviceInfoRelVo();
                    BeanUtils.copyProperties(deviceInfo,deviceInfoRelVo);
                    switch (deviceInfo.getDeviceType()){
                        case SYSTEM_ID_1:
                            deviceInfoRelVo.setDeviceType(CONFIG_DEVICE_TYPE_2);
                            levelTwo.add(deviceInfoRelVo);
                            break;
                        case SYSTEM_ID_2:
                            deviceInfoRelVo.setDeviceType(CONFIG_DEVICE_TYPE_3);
                            levelOne.add(deviceInfoRelVo);
                            break;
                        case SYSTEM_ID_3:
                            deviceInfoRelVo.setDeviceType(CONFIG_DEVICE_TYPE_4);
                            levelTwo.add(deviceInfoRelVo);
                            break;
                        default:
                            deviceInfoRelVo.setDeviceType(null);
                            break;
                    }
                    userCount = addUserCount(userCount, deviceInfo.getDeviceId());
                    totalBandwidth = addTotalBandwidth(totalBandwidth, deviceInfo.getDeviceId());
                }
                deviceInfoMap.put(LEVEL_1,levelOne);
                deviceInfoMap.put(LEVEL_2,levelTwo);
            }


            if (!CollectionUtils.isEmpty(deviceInfoMap)){
                for (Map.Entry<Integer, List<DeviceInfoRelVo>> entry : deviceInfoMap.entrySet()){
                    List<DeviceInfoRelVo> baseInfos = entry.getValue();
                    if (!CollectionUtils.isEmpty(baseInfos)){
                        // 找同级设备
                        for (DeviceInfoRelVo baseInfo : baseInfos){
                            List<NmplDeviceInfo> sameLevelInfo = nmplDeviceExtMapper.selectBaseStationListByMainDeviceId(baseInfo.getDeviceId(), roId);
                            if (!CollectionUtils.isEmpty(sameLevelInfo)){
                                List<DeviceInfoRelVo> sameLevelInfos = new ArrayList<>(sameLevelInfo.size());
                                for (NmplDeviceInfo deviceInfo : sameLevelInfo){
                                    DeviceInfoRelVo deviceInfoRelVo = new DeviceInfoRelVo();
                                    BeanUtils.copyProperties(deviceInfo,deviceInfoRelVo);
                                    sameLevelInfos.add(deviceInfoRelVo);
                                }
                                baseInfo.setRelDevices(sameLevelInfos);
                            }
                        }

                        // 找子级设备
                        for (DeviceInfoRelVo baseInfo : baseInfos){
                            List<NmplDeviceInfo> childLevelInfo = nmplDeviceExtMapper.selectDeviceListByMainDeviceId(baseInfo.getDeviceId(), roId);
                            if (!CollectionUtils.isEmpty(childLevelInfo)){
                                List<DeviceInfoRelVo> childLevelInfos = new ArrayList<>(childLevelInfo.size());
                                for (NmplDeviceInfo deviceInfo : childLevelInfo){
                                    DeviceInfoRelVo deviceInfoRelVo = new DeviceInfoRelVo();
                                    BeanUtils.copyProperties(deviceInfo,deviceInfoRelVo);
                                    childLevelInfos.add(deviceInfoRelVo);
                                }
                                baseInfo.setChildrenDevices(childLevelInfos);
                            }
                        }
                    }
                }
            }

            resp.setDeviceInfoMap(deviceInfoMap);
            result = buildResult(resp);
        }catch (Exception e){
            log.error("MonitorServiceImpl.queryMonitor Exception:{}",e.getMessage());
            result = failResult(e);
        }
        return result;
    }

    @Override
    public Result<TotalLoadChangeResp> totalLoadChange(TotalLoadChangeReq req) {
        Result result;

        try {
            TotalLoadChangeResp resp = null;
            totalLoadChangeParam(req);

            switch (req.getBigType()){
                case DEVICE_BIG_TYPE_0:
                    NmplBaseStationInfoExample bexample = new NmplBaseStationInfoExample();
                    bexample.createCriteria().andStationTypeEqualTo(req.getDeviceType()).andRelationOperatorIdEqualTo(req.getRoId()).andIsExistEqualTo(DataConstants.IS_EXIST);
                    List<NmplBaseStationInfo> stationInfos = nmplBaseStationInfoMapper.selectByExample(bexample);
                    if (!CollectionUtils.isEmpty(stationInfos)){
                        Map<String, List> dataMap = new HashMap<>();
                        List<Long> inTotalLoadVos = new ArrayList<>(24*60/15);
                        List<Long> outTotalLoadVos = new ArrayList<>(24*60/15);
                        List<String> timeString = new ArrayList<>(24*60/15);

                        Date startTime = DateUtils.getStartForDay(new Date());
                        for (int i=0; i<24*60/SPLIT_TIME ; i++){
                            BigDecimal inTotalLoad = BigDecimal.ZERO;
                            BigDecimal outTotalLoad = BigDecimal.ZERO;
                            Date endTime = DateUtils.addMinuteForDate(startTime, SPLIT_TIME);
                            for (NmplBaseStationInfo stationInfo : stationInfos){
                                // 查询设备的内网总带宽
                                BigDecimal decimal1 = nmplDataCollectExtMapper.countLoad(stationInfo.getStationId(), INTRANET_BROADBAND_LOAD_CODE, startTime, endTime);
                                if (decimal1 != null){
                                    inTotalLoad = inTotalLoad.add(decimal1);
                                }

                                // 查询设备的外网总带宽
                                BigDecimal decimal2 = nmplDataCollectExtMapper.countLoad(stationInfo.getStationId(), INTERNET_BROADBAND_LOAD_CODE, startTime, endTime);
                                if (decimal2 != null){
                                    outTotalLoad = outTotalLoad.add(decimal2);
                                }
                            }
                            inTotalLoadVos.add(inTotalLoad.longValueExact());
                            outTotalLoadVos.add(outTotalLoad.longValueExact());
                            timeString.add(DateUtils.dateToString(endTime, DateUtils.MINUTE_TIME_FORMAT));
                            startTime = endTime;
                        }
                        dataMap.put(INTRANET_BROADBAND_LOAD_CODE,inTotalLoadVos);
                        dataMap.put(INTERNET_BROADBAND_LOAD_CODE,outTotalLoadVos);
                        dataMap.put(TIMER_SHAFT,timeString);
                        resp = new TotalLoadChangeResp();
                        resp.setDataMap(dataMap);
                    }
                    break;
                case DEVICE_BIG_TYPE_1:
                    NmplDeviceInfoExample dexample = new NmplDeviceInfoExample();
                    dexample.createCriteria().andDeviceTypeEqualTo(req.getDeviceType()).andRelationOperatorIdEqualTo(req.getRoId()).andIsExistEqualTo(DataConstants.IS_EXIST);
                    List<NmplDeviceInfo> deviceInfos = nmplDeviceInfoMapper.selectByExample(dexample);
                    if (!CollectionUtils.isEmpty(deviceInfos)){
                        Map<String, List> dataMap = new HashMap<>();
                        List<Long> inTotalLoadVos = new ArrayList<>(24*60/15);
                        List<Long> outTotalLoadVos = new ArrayList<>(24*60/15);
                        List<String> timeString = new ArrayList<>(24*60/15);

                        Date startTime = DateUtils.getStartForDay(new Date());
                        for (int i=0; i<24*60/SPLIT_TIME; i++){
                            BigDecimal inTotalLoad = BigDecimal.ZERO;
                            BigDecimal outTotalLoad = BigDecimal.ZERO;
                            Date endTime = DateUtils.addMinuteForDate(startTime, SPLIT_TIME);
                            for (NmplDeviceInfo deviceInfo : deviceInfos){
                                // 查询设备的内网总带宽
                                BigDecimal decimal1 = nmplDataCollectExtMapper.countLoad(deviceInfo.getDeviceId(), INTRANET_BROADBAND_LOAD_CODE, startTime, endTime);
                                if (decimal1 != null){
                                    inTotalLoad = inTotalLoad.add(decimal1);
                                }

                                // 查询设备的外网总带宽
                                BigDecimal decimal2 = nmplDataCollectExtMapper.countLoad(deviceInfo.getDeviceId(), INTERNET_BROADBAND_LOAD_CODE, startTime, endTime);
                                if (decimal2 != null){
                                    outTotalLoad = outTotalLoad.add(decimal2);
                                }
                            }
                            inTotalLoadVos.add(inTotalLoad.longValueExact());
                            outTotalLoadVos.add(outTotalLoad.longValueExact());
                            timeString.add(DateUtils.dateToString(endTime, DateUtils.MINUTE_TIME_FORMAT));
                            startTime = endTime;
                        }
                        dataMap.put(INTRANET_BROADBAND_LOAD_CODE,inTotalLoadVos);
                        dataMap.put(INTERNET_BROADBAND_LOAD_CODE,outTotalLoadVos);
                        dataMap.put(TIMER_SHAFT,timeString);
                        resp = new TotalLoadChangeResp();
                        resp.setDataMap(dataMap);
                    }
                    break;
                default:
                    log.error("MonitorServiceImpl.totalLoadChange error: 设备大类{}",req.getBigType());
                    throw new SystemException(ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG);
            }
            result = buildResult(resp);
        }catch (Exception e){
            log.error("MonitorServiceImpl.totalLoadChange Exception:{}",e.getMessage());
            result = failResult(e);
        }

        return result;
    }


    /**
     * 1：在线 2：未激活 3：下线 4:其他
     * @param deviceId
     * @return
     *
     */
    private Map<String,String> checkStationStatus(String deviceId){
        Map<String,String> resultMap = new HashMap<>(2);

        // 先查看缓存是否在线
        try {
            Object key = redisTemplate.opsForValue().get(HEART_CHECK_DEVICE_ID+deviceId);

            if (key != null){
                resultMap.put("status",STATION_STATUS_ACTIVE);
                return resultMap;
            }
        }catch (Exception e){
            log.warn("get heart_check_device_id error,id:{},msg:{}",deviceId,e.getMessage());
        }

        // 查看设备是否存在
        NmplBaseStationInfoExample example = new NmplBaseStationInfoExample();
        example.createCriteria().andStationIdEqualTo(deviceId).andIsExistEqualTo(DataConstants.IS_EXIST);
        List<NmplBaseStationInfo> stationInfos = nmplBaseStationInfoMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(stationInfos)){
            NmplDeviceInfoExample dExample = new NmplDeviceInfoExample();
            dExample.createCriteria().andDeviceIdEqualTo(deviceId).andIsExistEqualTo(DataConstants.IS_EXIST);
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

    private Long addUserCount(Long userCount,String deviceId){
        NmplDataCollectExample example = new NmplDataCollectExample();
        example.setOrderByClause("upload_time desc");
        example.createCriteria().andDeviceIdEqualTo(deviceId).andDataItemCodeEqualTo(USER_COUNT_CODE).andUploadTimeGreaterThan(DateUtils.addMinuteForDate(new Date(),-USER_COUNT_TIME));
        List<NmplDataCollect> dataCollects = nmplDataCollectMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(dataCollects)){
            String dataItemValue = dataCollects.get(0).getDataItemValue();
            userCount = userCount + Long.valueOf(dataItemValue);
        }
        return userCount;
    }

    private Long addTotalBandwidth(Long totalBandwidth,String deviceId){
        NmplDataCollectExample example = new NmplDataCollectExample();
        example.setOrderByClause("upload_time desc");
        example.createCriteria().andDeviceIdEqualTo(deviceId).andDataItemCodeEqualTo(TOTAL_BAND_WIDTH_CODE).andUploadTimeGreaterThan(DateUtils.addMinuteForDate(new Date(),-TOTAL_BAND_WIDTH_TIME));
        List<NmplDataCollect> dataCollects = nmplDataCollectMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(dataCollects)){
            String dataItemValue = dataCollects.get(0).getDataItemValue();
            totalBandwidth = totalBandwidth + Long.valueOf(dataItemValue);
        }
        return totalBandwidth;
    }

    private void checkHeartParam(CheckHeartReq req) {
        if (ParamCheckUtil.checkVoStrBlank(req.getDeviceId())){
            throw new SystemException(ErrorCode.PARAM_IS_NULL, "deviceId"+ ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }

    private void queryMonitorParam(QueryMonitorReq req) {
        if (ParamCheckUtil.checkVoStrBlank(req.getRoId())){
            throw new SystemException(ErrorCode.PARAM_IS_NULL, "roId"+ ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }

    private void totalLoadChangeParam(TotalLoadChangeReq req) {
        if (ParamCheckUtil.checkVoStrBlank(req.getRoId())){
            throw new SystemException(ErrorCode.PARAM_IS_NULL, "roId"+ ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (ParamCheckUtil.checkVoStrBlank(req.getBigType())){
            throw new SystemException(ErrorCode.PARAM_IS_NULL, "bigType"+ ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (ParamCheckUtil.checkVoStrBlank(req.getDeviceType())){
            throw new SystemException(ErrorCode.PARAM_IS_NULL, "deviceType"+ ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }


}
