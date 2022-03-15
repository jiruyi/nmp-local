package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.SystemException;
import com.matrictime.network.constant.DataConstants;
import com.matrictime.network.dao.mapper.NmplBaseStationInfoMapper;
import com.matrictime.network.dao.mapper.NmplDeviceInfoMapper;
import com.matrictime.network.dao.model.NmplBaseStationInfo;
import com.matrictime.network.dao.model.NmplBaseStationInfoExample;
import com.matrictime.network.dao.model.NmplDeviceInfo;
import com.matrictime.network.dao.model.NmplDeviceInfoExample;
import com.matrictime.network.exception.ErrorCode;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.CheckHeartReq;
import com.matrictime.network.request.QueryMonitorReq;
import com.matrictime.network.response.CheckHeartResp;
import com.matrictime.network.response.QueryMonitorResp;
import com.matrictime.network.service.MonitorService;
import com.matrictime.network.util.ParamCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Override
    public Result<CheckHeartResp> checkHeart(CheckHeartReq req) {
        Result result;
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
                    log.error("checkHeart error: 设备id:{},状态为{}",req.getDeviceId(),status);
                    throw new SystemException(ErrorMessageContants.DEVICE_NOT_ACTIVE_MSG);
            }

            CheckHeartResp resp = new CheckHeartResp();
            result = buildResult(resp);
        }catch (Exception e){
            log.error(e.getMessage());
            result = failResult(e);
        }

        return result;
    }

    @Override
    public Result<QueryMonitorResp> queryMonitor(QueryMonitorReq req) {
        return null;
    }

    /**
     * 1：在线 2：未激活 3：下线 4:其他
     * @param deviceId
     * @return
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
//        if (ParamCheckUtil.checkVoStrBlank(req.getStatus())){
//            throw new SystemException(ErrorCode.PARAM_IS_NULL, "status"+ ErrorMessageContants.PARAM_IS_NULL_MSG);
//        }
    }
}
