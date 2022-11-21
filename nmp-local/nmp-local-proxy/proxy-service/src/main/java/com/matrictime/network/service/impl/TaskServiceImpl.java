package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.base.constant.DataConstants;
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

    @Resource
    private NmplBillMapper nmplBillMapper;

    @Resource
    private NmplErrorPushLogMapper nmplErrorPushLogMapper;

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
            Boolean flag = false;
            String post = null;
            String data = "";
            String msg= null;
            try {
                data = JSON.toJSONString(nmplDeviceLogs);
                post = HttpClientUtil.post(url, data);
                log.info("logPush result:{}",post);
            }catch (Exception e){
                msg = e.getMessage();
                log.info("logPush Exception:{}",e.getMessage());
                flag = true;
            }finally {
                logError(post,url,data,flag,msg);
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
            Boolean flag = false;
            String post = null;
            String data = "";
            String msg = null;
            try {
                JSONObject req = new JSONObject();
                req.put("nmplPcDataVoList",nmplPcData);
                data = req.toJSONString();
                post = HttpClientUtil.post(url,data);
                log.info("pcData:{}",post);
            }catch (Exception e){
                msg = e.getMessage();
                log.info("pcData Exception:{}",e.getMessage());
                flag = true;
            }finally {
                logError(post,url,data,flag,msg);
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
            Boolean flag = false;
            String post = null;
            String data = "";
            String msg =null;
            try {
                JSONObject req = new JSONObject();
                req.put("dataCollectVoList",nmplDataCollectList);
                data = req.toJSONString();
                post = HttpClientUtil.post(url, data);
                log.info("dataCollect push result:{}",post);
            }catch (Exception e){
                flag = true;
                msg =e.getMessage();
                log.info("ddataCollect push Exception:{}",e.getMessage());
            }finally {
                logError(post,url,data,flag,msg);
            }
            criteria.andIdLessThanOrEqualTo(maxId);
            nmplDataCollectMapper.deleteByExample(nmplDataCollectExample);
        }
    }


    @Override
    public void billPush(String url) {
        NmplBillExample nmplBillExample = new NmplBillExample();
        NmplBillExample.Criteria criteria = nmplBillExample.createCriteria();
        nmplBillExample.setOrderByClause("id desc");
        List<NmplBill> nmplBills = nmplBillMapper.selectByExample(nmplBillExample);
        if(!CollectionUtils.isEmpty(nmplBills)){
            Long maxId = nmplBills.get(0).getId();
            Boolean flag = false;
            String post = null;
            String data = "";
            String msg =null;
            try {
                JSONObject req = new JSONObject();
                req.put("nmplBillVoList",nmplBills);
                data = req.toJSONString();
                post = HttpClientUtil.post(url,data);
                log.info("Bill push result:{}",post);
            }catch (Exception e){
                flag = true;
                msg = e.getMessage();
                log.info("Bill push Exception:{}",e.getMessage());
            }finally {
                logError(post,url,data,flag,msg);
            }
            criteria.andIdLessThanOrEqualTo(maxId);
            nmplBillMapper.deleteByExample(nmplBillExample);
        }

    }

    /**
     * @param post http返回体
     * @param url  推送路径
     * @param data 推送数据
     * @param flag 是否出现异常
     * @param msg  异常信息
     */
    private void logError(String post,String url,String data,Boolean flag,String msg){
        Boolean sucess = true;
        if(post!=null){
            JSONObject resp = JSONObject.parseObject(post);
            if (resp.containsKey("success")){
                sucess = (Boolean)resp.get("success");
            }
            if(null == msg){
                if (resp.containsKey("errorMsg")){
                    msg = (String) resp.get("errorMsg");
                }
            }
        }
        if(msg!=null&&msg.length()> DataConstants.ERROR_MSG_MAXLENGTH){
            msg = msg.substring(DataConstants.ZERO,DataConstants.ERROR_MSG_MAXLENGTH);
        }
        //推送失败或出现异常时记录
        if(!sucess||flag){
            NmplErrorPushLog nmplErrorPushLog = new NmplErrorPushLog();
            nmplErrorPushLog.setUrl(url);
            nmplErrorPushLog.setData(data);
            nmplErrorPushLog.setErrorMsg(msg);
            nmplErrorPushLogMapper.insertSelective(nmplErrorPushLog);
        }
    }

}
