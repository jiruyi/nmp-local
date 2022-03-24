package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.SystemException;
import com.matrictime.network.constant.DataConstants;
import com.matrictime.network.dao.mapper.NmplBaseStationInfoMapper;
import com.matrictime.network.dao.mapper.NmplDeviceInfoMapper;
import com.matrictime.network.dao.mapper.extend.NmplDataCollectExtMapper;
import com.matrictime.network.dao.mapper.extend.NmplDeviceExtMapper;
import com.matrictime.network.dao.model.NmplBaseStationInfo;
import com.matrictime.network.dao.model.NmplBaseStationInfoExample;
import com.matrictime.network.dao.model.NmplDeviceInfo;
import com.matrictime.network.dao.model.NmplDeviceInfoExample;
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
            QueryMonitorResp resp = null;
            queryMonitorParam(req);
            String roId = req.getRoId();
            NmplBaseStationInfoExample baseStationInfoExample = new NmplBaseStationInfoExample();
            baseStationInfoExample.createCriteria().andIsExistEqualTo(DataConstants.IS_EXIST).andRelationOperatorIdEqualTo(roId);
            List<NmplBaseStationInfo> baseStationInfos = nmplBaseStationInfoMapper.selectByExample(baseStationInfoExample);
            // 基站层信息查询
            if (!CollectionUtils.isEmpty(baseStationInfos)){
                List<DeviceInfoRelVo> baseInfos = new ArrayList<>(baseStationInfos.size());
                for (NmplBaseStationInfo baseStationInfo : baseStationInfos){
                    DeviceInfoRelVo baseInfo = new DeviceInfoRelVo();
                    BeanUtils.copyProperties(baseStationInfo,baseInfo);

                    // 分发机/缓存机层信息查询
                    List<NmplDeviceInfo> deviceInfos = nmplDeviceExtMapper.selectDeviceListByMainDeviceId(baseStationInfo.getStationId(), roId);
                    if (!CollectionUtils.isEmpty(deviceInfos)){
                        List<DeviceInfoRelVo> middleInfos = new ArrayList<>(deviceInfos.size());
                        for (NmplDeviceInfo deviceInfo : deviceInfos){
                            DeviceInfoRelVo middleInfo = new DeviceInfoRelVo();
                            BeanUtils.copyProperties(deviceInfo,middleInfo);

                            // 生成机层信息查询
                            List<NmplDeviceInfo> deviceInfos1 = nmplDeviceExtMapper.selectDeviceListByMainDeviceId(deviceInfo.getDeviceId(), roId);
                            if(!CollectionUtils.isEmpty(deviceInfos1)){
                                List<DeviceInfoRelVo> topInfos = new ArrayList<>(deviceInfos1.size());
                                for (NmplDeviceInfo deviceInfo1 : deviceInfos1){
                                    DeviceInfoRelVo topInfo = new DeviceInfoRelVo();
                                    BeanUtils.copyProperties(deviceInfo1,topInfo);
                                    topInfos.add(topInfo);
                                }
                                middleInfo.setChildrenDevices(topInfos);
                            }
                            middleInfos.add(middleInfo);
                        }
                        baseInfo.setChildrenDevices(middleInfos);
                    }

                    // 查询同级关联
                    List<NmplBaseStationInfo> baseStationRelList = nmplDeviceExtMapper.selectBaseStationListByMainDeviceId(baseStationInfo.getStationId(), roId);
                    if (!CollectionUtils.isEmpty(baseStationRelList)){
                        List<DeviceInfoRelVo> relDevices = new ArrayList<>(baseStationRelList.size());
                        for (NmplBaseStationInfo baseStationRel : baseStationRelList){
                            DeviceInfoRelVo relDevice = new DeviceInfoRelVo();
                            BeanUtils.copyProperties(baseStationRel,relDevice);
                            relDevices.add(relDevice);
                        }
                        baseInfo.setRelDevices(relDevices);
                    }
                }
                resp = new QueryMonitorResp();
                resp.setDeviceInfoRelVos(baseInfos);
            }

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
                        Map<String, List<TotalLoadVo>> dataMap = new HashMap<>(stationInfos.size());
                        for (NmplBaseStationInfo stationInfo : stationInfos){
                            List<TotalLoadVo> totalLoadVos = new ArrayList<>(24*60/15);
                            Date startTime = DateUtils.getStartForDay(new Date());
                            for (int i=15; i<24*60 ; i=i+15){
                                TotalLoadVo totalLoadVo = new TotalLoadVo();
                                Date endTime = DateUtils.addMinuteForDate(startTime, i);
                                BigDecimal totalLoad = nmplDataCollectExtMapper.countLoad(stationInfo.getStationId(), startTime, endTime);
                                totalLoadVo.setData(totalLoad.longValueExact());
                                totalLoadVos.add(totalLoadVo);
                                startTime = endTime;
                            }
                            dataMap.put(stationInfo.getStationId(),totalLoadVos);
                        }
                        resp = new TotalLoadChangeResp();
                        resp.setDataMap(dataMap);
                    }
                    break;
                case DEVICE_BIG_TYPE_1:
                    NmplDeviceInfoExample dexample = new NmplDeviceInfoExample();
                    dexample.createCriteria().andDeviceTypeEqualTo(req.getDeviceType()).andRelationOperatorIdEqualTo(req.getRoId()).andIsExistEqualTo(DataConstants.IS_EXIST);
                    List<NmplDeviceInfo> deviceInfos = nmplDeviceInfoMapper.selectByExample(dexample);
                    if (!CollectionUtils.isEmpty(deviceInfos)){
                        Map<String, List<TotalLoadVo>> dataMap = new HashMap<>(deviceInfos.size());
                        for (NmplDeviceInfo deviceInfo : deviceInfos){
                            List<TotalLoadVo> totalLoadVos = new ArrayList<>(24*60/15);
                            Date startTime = DateUtils.getStartForDay(new Date());
                            for (int i=15; i<24*60 ; i=i+15){
                                TotalLoadVo totalLoadVo = new TotalLoadVo();
                                Date endTime = DateUtils.addMinuteForDate(startTime, i);
                                BigDecimal totalLoad = nmplDataCollectExtMapper.countLoad(deviceInfo.getDeviceId(), startTime, endTime);
                                totalLoadVo.setData(totalLoad.longValueExact());
                                totalLoadVos.add(totalLoadVo);
                                startTime = endTime;
                            }
                            dataMap.put(deviceInfo.getDeviceId(),totalLoadVos);
                        }
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
