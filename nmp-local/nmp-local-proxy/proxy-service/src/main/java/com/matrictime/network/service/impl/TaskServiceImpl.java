package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.dao.mapper.NmplKeycenterHeartInfoMapper;
import com.matrictime.network.dao.mapper.NmplStationHeartInfoMapper;
import com.matrictime.network.dao.model.NmplKeycenterHeartInfo;
import com.matrictime.network.dao.model.NmplKeycenterHeartInfoExample;
import com.matrictime.network.dao.model.NmplStationHeartInfo;
import com.matrictime.network.dao.model.NmplStationHeartInfoExample;
import com.matrictime.network.service.TaskService;
import com.matrictime.network.util.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class TaskServiceImpl implements TaskService {

    @Resource
    private NmplStationHeartInfoMapper nmplStationHeartInfoMapper;

    @Resource
    private NmplKeycenterHeartInfoMapper nmplKeycenterHeartInfoMapper;

    @Override
    public void heartReport(String url) {
        Date now = new Date();
        NmplStationHeartInfoExample stationExample = new NmplStationHeartInfoExample();
        stationExample.createCriteria().andCreateTimeLessThanOrEqualTo(now);
        List<NmplStationHeartInfo> stationHeartInfos = nmplStationHeartInfoMapper.selectByExample(stationExample);
        if (!CollectionUtils.isEmpty(stationHeartInfos)){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("deviceId",stationHeartInfos.get(0).getStationId());
            jsonObject.put("status", NumberUtils.INTEGER_ONE);
            try {
                HttpClientUtil.post(url,jsonObject.toJSONString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            int deleteStation = nmplStationHeartInfoMapper.deleteByExample(stationExample);
            log.info("TaskServiceImpl.heartReport deleteStation:{}"+deleteStation);
        }

        NmplKeycenterHeartInfoExample keycenterExample = new NmplKeycenterHeartInfoExample();
        keycenterExample.createCriteria().andCreateTimeLessThanOrEqualTo(now);
        List<NmplKeycenterHeartInfo> keycenterHeartInfos = nmplKeycenterHeartInfoMapper.selectByExample(keycenterExample);
        if (!CollectionUtils.isEmpty(keycenterHeartInfos)){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("deviceId",keycenterHeartInfos.get(0).getDeviceId());
            jsonObject.put("status", NumberUtils.INTEGER_ONE);
            try {
                HttpClientUtil.post(url,jsonObject.toJSONString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            int deleteKeycenter = nmplKeycenterHeartInfoMapper.deleteByExample(keycenterExample);
            log.info("TaskServiceImpl.heartReport deleteKeycenter:{}"+deleteKeycenter);
        }
    }
}
