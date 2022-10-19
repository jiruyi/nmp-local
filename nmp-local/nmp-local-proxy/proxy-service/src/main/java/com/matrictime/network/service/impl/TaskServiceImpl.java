package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.dao.mapper.*;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.service.TaskService;
import com.matrictime.network.util.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class TaskServiceImpl implements TaskService {

    @Resource
    private NmplStationHeartInfoMapper nmplStationHeartInfoMapper;

    @Resource
    private NmplKeycenterHeartInfoMapper nmplKeycenterHeartInfoMapper;

    @Resource
    private NmplDeviceLogMapper nmplDeviceLogMapper;

    @Resource
    private NmplPcDataMapper nmplPcDataMapper;

    @Resource
    private NmplDataCollectMapper nmplDataCollectMapper;

    @Override
    public void heartReport(String url) {
        NmplStationHeartInfoExample stationExample = new NmplStationHeartInfoExample();
        stationExample.setOrderByClause("create_time desc");
        List<NmplStationHeartInfo> stationHeartInfos = nmplStationHeartInfoMapper.selectByExample(stationExample);
        if (!CollectionUtils.isEmpty(stationHeartInfos)){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("deviceId",stationHeartInfos.get(NumberUtils.INTEGER_ZERO).getStationId());
            jsonObject.put("status", stationHeartInfos.get(NumberUtils.INTEGER_ZERO).getRemark());
            try {
                HttpClientUtil.post(url,jsonObject.toJSONString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stationExample.createCriteria().andCreateTimeLessThanOrEqualTo(stationHeartInfos.get(NumberUtils.INTEGER_ZERO).getCreateTime());
            int deleteStation = nmplStationHeartInfoMapper.deleteByExample(stationExample);
            log.info("TaskServiceImpl.heartReport deleteStation:{}"+deleteStation);
        }

        NmplKeycenterHeartInfoExample keycenterExample = new NmplKeycenterHeartInfoExample();
        keycenterExample.setOrderByClause("create_time desc");
        List<NmplKeycenterHeartInfo> keycenterHeartInfos = nmplKeycenterHeartInfoMapper.selectByExample(keycenterExample);
        if (!CollectionUtils.isEmpty(keycenterHeartInfos)){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("deviceId",keycenterHeartInfos.get(NumberUtils.INTEGER_ZERO).getDeviceId());
            jsonObject.put("status", keycenterHeartInfos.get(NumberUtils.INTEGER_ZERO).getRemark());
            try {
                HttpClientUtil.post(url,jsonObject.toJSONString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            keycenterExample.createCriteria().andCreateTimeLessThanOrEqualTo(keycenterHeartInfos.get(NumberUtils.INTEGER_ZERO).getCreateTime());
            int deleteKeycenter = nmplKeycenterHeartInfoMapper.deleteByExample(keycenterExample);
            log.info("TaskServiceImpl.heartReport deleteKeycenter:{}"+deleteKeycenter);
        }
    }

    @Override
    public void logPush(String url) {
        NmplDeviceLogExample nmplDeviceLogExample = new NmplDeviceLogExample();
        NmplDeviceLogExample.Criteria criteria = nmplDeviceLogExample.createCriteria();
        nmplDeviceLogExample.setOrderByClause("id desc");
        List<NmplDeviceLog> nmplDeviceLogs = nmplDeviceLogMapper.selectByExample(nmplDeviceLogExample);

        if(!CollectionUtils.isEmpty(nmplDeviceLogs)){
            try {
                String post = HttpClientUtil.post(url, JSON.toJSONString(nmplDeviceLogs));
                log.info("logPush result:{}",post);
            }catch (Exception e){
                log.info("logPush Exception:{}",e.getMessage());
                throw new RuntimeException(e);
            }
            //删除已经推送的日志
            criteria.andIdLessThan(nmplDeviceLogs.get(NumberUtils.INTEGER_ZERO).getId());
            nmplDeviceLogMapper.deleteByExample(nmplDeviceLogExample);
        }
    }

    @Override
    public void pcData(String url) {
        NmplPcDataExample nmplPcDataExample = new NmplPcDataExample();
        NmplPcDataExample.Criteria criteria = nmplPcDataExample.createCriteria();
        nmplPcDataExample.setOrderByClause("id desc");
        List<NmplPcData> nmplPcData = nmplPcDataMapper.selectByExample(nmplPcDataExample);
        if (!CollectionUtils.isEmpty(nmplPcData)){
            try {
                JSONObject req = new JSONObject();
                req.put("nmplPcDataVoList",nmplPcData);
                String post = HttpClientUtil.post(url, req.toJSONString());
                log.info("pcData:{}",post);
            }catch (Exception e){
                log.info("pcData Exception:{}",e.getMessage());
                throw new RuntimeException(e);
            }
            criteria.andIdLessThanOrEqualTo(nmplPcData.get(NumberUtils.INTEGER_ZERO).getId());
            nmplPcDataMapper.deleteByExample(nmplPcDataExample);
        }
    }


    @Override
    public void dataCollectPush(String url) {
        NmplDataCollectExample nmplDataCollectExample = new NmplDataCollectExample();
        NmplDataCollectExample.Criteria criteria = nmplDataCollectExample.createCriteria();
        nmplDataCollectExample.setOrderByClause("id desc");

        List<NmplDataCollect> nmplDataCollectList = nmplDataCollectMapper.selectByExample(nmplDataCollectExample);
        if(!CollectionUtils.isEmpty(nmplDataCollectList)){
            Long maxId = nmplDataCollectList.get(0).getId();
            try {
                JSONObject req = new JSONObject();
                req.put("dataCollectVoList",nmplDataCollectList);
                String post = HttpClientUtil.post(url, req.toJSONString());
                log.info("nmplPcDataVoList:{}",post);
            }catch (Exception e){
                log.info("nmplPcDataVoList Exception:{}",e.getMessage());
                throw new RuntimeException(e);
            }
            criteria.andIdLessThanOrEqualTo(maxId);
            nmplDataCollectMapper.deleteByExample(nmplDataCollectExample);
        }
    }
}
