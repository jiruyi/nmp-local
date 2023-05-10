package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.constant.DataConstants;
import com.matrictime.network.dao.domain.AlarmDataDomainService;
import com.matrictime.network.dao.mapper.*;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.model.AlarmInfo;
import com.matrictime.network.util.DateUtils;
import com.matrictime.network.util.FileHahUtil;
import com.matrictime.network.util.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

import static com.matrictime.network.base.constant.DataConstants.*;
import static com.matrictime.network.constant.DataConstants.IS_EXIST;

@Service
@Slf4j
public class AsyncService{

    @Autowired(required = false)
    private NmplSignalIoMapper nmplSignalIoMapper;

    @Autowired(required = false)
    private NmplFileDeviceRelMapper nmplFileDeviceRelMapper;

    @Resource
    private NmplVersionInfoMapper nmplVersionInfoMapper;

    @Resource
    private NmplDeviceMapper nmplDeviceMapper;

    @Resource
    private NmplBaseStationMapper nmplBaseStationMapper;


    @Value("${asynservice.isremote}")
    private Integer isremote;

    @Autowired
    private AlarmDataDomainService alarmDataDomainService;

    @Async("taskExecutor")
    public void testInsertAlarmData(){
        List<AlarmInfo> alarmInfoList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            alarmInfoList.add(AlarmInfo.builder().alarmSourceIp("192.168.72.103").alarmUploadTime(DateUtils.addDayForNow(-i)).alarmContentType("5").alarmSourceType("01").alarmLevel("1").alarmContent("系统崩溃").build());
            alarmInfoList.add(AlarmInfo.builder().alarmSourceIp("192.168.72.103").alarmUploadTime(DateUtils.addDayForNow(-i)).alarmContentType("5").alarmSourceType("01").alarmLevel("2").alarmContent("系统塌了").build());
            alarmInfoList.add(AlarmInfo.builder().alarmSourceIp("192.168.72.103").alarmUploadTime(DateUtils.addDayForNow(-i)).alarmContentType("5").alarmSourceType("01").alarmLevel("3").alarmContent("系统塌了").build());
            alarmInfoList.add(AlarmInfo.builder().alarmSourceIp("192.168.72.20").alarmUploadTime(DateUtils.addDayForNow(-i)).alarmContentType("5").alarmSourceType("01").alarmLevel("1").alarmContent("系统塌了").build());
            alarmInfoList.add(AlarmInfo.builder().alarmSourceIp("192.168.72.24").alarmUploadTime(DateUtils.addDayForNow(-i)).alarmContentType("5").alarmSourceType("02").alarmLevel("2").alarmContent("系统塌了").build());
            alarmInfoList.add(AlarmInfo.builder().alarmSourceIp("192.168.72.24").alarmUploadTime(DateUtils.addDayForNow(-i)).alarmContentType("5").alarmSourceType("02").alarmLevel("3").alarmContent("系统塌了").build());
            alarmInfoList.add(AlarmInfo.builder().alarmSourceIp("192.168.72.24").alarmUploadTime(DateUtils.addDayForNow(-i)).alarmContentType("5").alarmSourceType("02").alarmLevel("1").alarmContent("系统塌了").build());
            alarmInfoList.add(AlarmInfo.builder().alarmSourceIp("192.168.72.23").alarmUploadTime(DateUtils.addDayForNow(-i)).alarmContentType("5").alarmSourceType("11").alarmLevel("1").alarmContent("系统塌了").build());
            alarmInfoList.add(AlarmInfo.builder().alarmSourceIp("192.168.72.23").alarmUploadTime(DateUtils.addDayForNow(-i)).alarmContentType("5").alarmSourceType("11").alarmLevel("2").alarmContent("系统塌了").build());
            alarmInfoList.add(AlarmInfo.builder().alarmSourceIp("192.168.72.23").alarmUploadTime(DateUtils.addDayForNow(-i)).alarmContentType("5").alarmSourceType("11").alarmLevel("3").alarmContent("系统塌了").build());
            alarmInfoList.add(AlarmInfo.builder().alarmSourceIp("192.168.72.23").alarmUploadTime(DateUtils.addDayForNow(-i)).alarmContentType("5").alarmSourceType("11").alarmLevel("3").alarmContent("系统塌了").build());
            alarmInfoList.add(AlarmInfo.builder().alarmSourceIp("192.168.72.20").alarmUploadTime(DateUtils.addDayForNow(-i)).alarmSourceType("01").alarmLevel("2").alarmContentType("5").alarmContent("系统崩溃").build());
            alarmInfoList.add(AlarmInfo.builder().alarmSourceIp("192.168.72.20").alarmUploadTime(DateUtils.addDayForNow(-i)).alarmSourceType("01").alarmLevel("1").alarmContentType("5").alarmContent("系统塌了").build());
            alarmInfoList.add(AlarmInfo.builder().alarmSourceIp("192.168.72.21").alarmUploadTime(DateUtils.addDayForNow(-i)).alarmSourceType("00").alarmLevel("2").alarmContentType("1").alarmContent("cpu过高").build());
            alarmInfoList.add(AlarmInfo.builder().alarmSourceIp("192.168.72.21").alarmUploadTime(DateUtils.addDayForNow(-i)).alarmSourceType("00").alarmLevel("1").alarmContentType("2").alarmContent("内存不足").build());
            alarmInfoList.add(AlarmInfo.builder().alarmSourceIp("192.168.72.20").alarmUploadTime(DateUtils.addDayForNow(-i)).alarmSourceType("00").alarmLevel("2").alarmContentType("3").alarmContent("磁盘满载").build());
            alarmInfoList.add(AlarmInfo.builder().alarmSourceIp("192.168.72.20").alarmUploadTime(DateUtils.addDayForNow(-i)).alarmSourceType("00").alarmLevel("1").alarmContentType("4").alarmContent("流量过高").build());
        }
        alarmDataDomainService.acceptAlarmData(alarmInfoList);
    }


    @Async("taskExecutor")
    public Future<Map<String,List<String>>> httpSyncConfig(List<Map<String, String>> list) {

        Map<String,List<String>> result = new HashMap<>();
        List<String> successIds = new ArrayList<>(list.size());
        List<String> failIds = new ArrayList<>();
        String deviceId = "";
        try {
            for (Map<String,String> map : list){
                log.info("AsyncService.httpSyncConfig map:{},isremote:{}",JSONObject.toJSONString(map),isremote);
                deviceId = map.get(KEY_DEVICE_ID);
                String configCode = map.get(KEY_CONFIG_CODE);
                JSONObject jsonReq = new JSONObject();
                jsonReq.put(KEY_CONFIG_CODE,configCode);
                jsonReq.put(KEY_CONFIG_VALUE,map.get(KEY_CONFIG_VALUE));
                jsonReq.put(KEY_UNIT,map.get(KEY_UNIT));
                boolean flag = false;
                try{
                    // TODO: 2022/3/31 返回值暂时写死，配置同步需要和站点联调获取返回值
                    if (map.containsKey(KEY_URL)){
                        String post = "";
                        if (isremote == 1){
                            post = HttpClientUtil.post(map.get(KEY_URL), jsonReq.toJSONString());
                        }else {
                            post = "{\"isSuccess\":true}";
                        }
                        log.info("AsyncService.httpSyncConfig result deviceId:{},req:{},post:{}",deviceId,jsonReq.toJSONString(),post);
                        JSONObject jsonObject = JSONObject.parseObject(post);
                        if (jsonObject != null && jsonObject.get(KEY_IS_SUCCESS) instanceof Boolean){
                            flag = (Boolean) jsonObject.get(KEY_IS_SUCCESS);
                        }
                    }
                }catch (Exception e){
                    log.warn("httpSyncConfig.HttpClientUtil Exception:{},deviceId:{},map:{}",e.getMessage(),deviceId,JSONObject.toJSONString(map));
                }
                if (flag){
                    successIds.add(deviceId);
                }else {
                    failIds.add(deviceId);
                }
            }
        }catch (Exception e){
            log.warn("AsyncService.httpSyncConfig Exception:{}",e.getMessage());
        }
        result.put(KEY_SUCCESS_IDS,successIds);
        result.put(KEY_FAIL_IDS,failIds);
        return new AsyncResult<>(result);
    }


    @Async("taskExecutor")
    public Future<Map<String,List<String>>> httpSignalIo(List<Map<String, String>> list) {

        Map<String,List<String>> result = new HashMap<>();
        List<String> successIds = new ArrayList<>(list.size());
        List<String> failIds = new ArrayList<>();
        String deviceId = "";
        try {
            for (Map<String,String> map : list){
                log.info("AsyncService.httpSignalIo map:{},isremote:{}",JSONObject.toJSONString(map),isremote);
                deviceId = map.get(KEY_DEVICE_ID);
                String userId = map.get(KEY_USER_ID);
                String ioType = map.get(KEY_IO_TYPE);
                String url = map.get(KEY_URL);
                JSONObject jsonReq = new JSONObject();
                jsonReq.put(KEY_IO_TYPE,ioType);
                boolean flag = false;
                try{
                    if (map.containsKey(KEY_URL)) {
                        String postResp = "";
                        if (isremote == 1){
                            postResp = HttpClientUtil.post(url, jsonReq.toJSONString());
                        }else {
                            postResp = "{\"isSuccess\":true}";
                        }
                        log.info("AsyncService.httpSignalIo result deviceId:{},userId:{},req:{},postResp:{}", deviceId, userId, jsonReq.toJSONString(), postResp);
                        JSONObject jsonObject = JSONObject.parseObject(postResp);
                        if (jsonObject != null) {
                            Object success = jsonObject.get(KEY_IS_SUCCESS);
                            if (success != null && success instanceof Boolean) {
                                if ((Boolean) success) {
                                    flag = true;
                                }
                            }
                        }
                    }
                }catch (Exception e){
                    log.warn("httpSignalIo.HttpClientUtil Exception:{},deviceId:{},req:{}",e.getMessage(),deviceId,jsonReq.toJSONString());
                }
                if (flag){
                    successIds.add(deviceId);
                    // 调用站点接口成功则进行记录
                    NmplSignalIoExample example = new NmplSignalIoExample();
                    example.createCriteria().andDeviceIdEqualTo(deviceId).andUpdateUserEqualTo(userId);
                    List<NmplSignalIo> nmplSignalIos = nmplSignalIoMapper.selectByExample(example);

                    NmplSignalIo io = new NmplSignalIo();

                    if (!CollectionUtils.isEmpty(nmplSignalIos)){
                        io = nmplSignalIos.get(0);
                        io.setStatus(ioType);
                        io.setIsExist(DataConstants.IS_EXIST);
                        nmplSignalIoMapper.updateByPrimaryKey(io);
                    }else {
                        io.setDeviceId(deviceId);
                        io.setUpdateUser(userId);
                        io.setStatus(ioType);
                        nmplSignalIoMapper.insertSelective(io);
                    }
                }else {
                    failIds.add(deviceId);
                }

            }
        }catch (Exception e){
            log.warn("AsyncService.httpSignalIo Exception:{}",e.getMessage());
        }
        result.put(KEY_SUCCESS_IDS,successIds);
        result.put(KEY_FAIL_IDS,failIds);
        return new AsyncResult<>(result);
    }

    @Async("taskExecutor")
    public Future<Map<String,List<String>>> httpPushFile(List<Map<String, String>> list) {

        Map<String,List<String>> result = new HashMap<>();
        List<String> successIds = new ArrayList<>(list.size());
        List<String> failIds = new ArrayList<>();
        try {
            for (Map<String,String> map : list){
                log.info("AsyncService.httpPushFile map:{},isremote:{}",JSONObject.toJSONString(map),isremote);
                String deviceId = map.get(KEY_DEVICE_ID);
                String fileId = map.get(KEY_FILE_ID);
                String url = map.get(KEY_URL);
                String filePath = map.get(KEY_FILE_PATH);
                String fileName = map.get(KEY_FILE_NAME);

                JSONObject jsonReq = new JSONObject();
                jsonReq.put(KEY_FILE_ID,fileId);
                jsonReq.put(KEY_FILE_PATH,filePath);
                jsonReq.put(KEY_FILE_NAME,fileName);
                boolean flag = false;
                try{
                    if (map.containsKey(KEY_URL)) {
                        String postResp = "";
                        if (isremote == 1){
                            postResp = HttpClientUtil.post(url, jsonReq.toJSONString());
                        }else {
                            postResp = "{\"isSuccess\":true}";
                        }
                        log.info("AsyncService.httpPushFile result url:{},deviceId:{},req:{},postResp:{}", url, deviceId, jsonReq.toJSONString(), postResp);
                        JSONObject jsonObject = JSONObject.parseObject(postResp);
                        if (jsonObject != null) {
                            Object success = jsonObject.get(KEY_IS_SUCCESS);
                            if (success != null && success instanceof Boolean) {
                                if ((Boolean) success) {
                                    flag = true;
                                    NmplFileDeviceRelExample example = new NmplFileDeviceRelExample();
                                    example.createCriteria().andDeviceIdEqualTo(deviceId).andFileIdEqualTo(Long.parseLong(map.get(KEY_FILE_ID)));
                                    List<NmplFileDeviceRel> rels = nmplFileDeviceRelMapper.selectByExample(example);
                                    if (CollectionUtils.isEmpty(rels)) {
                                        NmplFileDeviceRel nmplFileDeviceRel = new NmplFileDeviceRel();
                                        nmplFileDeviceRel.setFileId(Long.parseLong(map.get(KEY_FILE_ID)));
                                        nmplFileDeviceRel.setDeviceId(deviceId);
                                        nmplFileDeviceRelMapper.insertSelective(nmplFileDeviceRel);
                                    } else if (!rels.get(0).getIsDelete()) {
                                        NmplFileDeviceRel nmplFileDeviceRel = new NmplFileDeviceRel();
                                        nmplFileDeviceRel.setId(rels.get(0).getId());
                                        nmplFileDeviceRel.setIsDelete(IS_EXIST);
                                        nmplFileDeviceRel.setUpdateTime(new Date());
                                        nmplFileDeviceRelMapper.updateByPrimaryKeySelective(nmplFileDeviceRel);
                                    }
                                }
                            }
                        }
                    }
                }catch (Exception e){
                    log.warn("httpPushFile.HttpClientUtil Exception:{},deviceId:{},req:{}",e.getMessage(),deviceId,jsonReq.toJSONString());
                }
                if (flag){
                    successIds.add(deviceId);
                }else {
                    failIds.add(deviceId);
                }

            }
        }catch (Exception e){
            log.warn("AsyncService.httpPushFile Exception:{}",e.getMessage());
        }
        result.put(KEY_SUCCESS_IDS,successIds);
        result.put(KEY_FAIL_IDS,failIds);
        return new AsyncResult<>(result);
    }


    @Async("taskExecutor")
    public Future<Map<String,List<String>>> httpStartFile(List<Map<String, String>> list) {

        Map<String,List<String>> result = new HashMap<>();
        List<String> successIds = new ArrayList<>(list.size());
        List<String> failIds = new ArrayList<>();
        try {
            for (Map<String,String> map : list){
                log.info("AsyncService.httpStartFile map:{},isremote:{}",JSONObject.toJSONString(map),isremote);
                String deviceId = map.get(KEY_DEVICE_ID);
                String fileId = map.get(KEY_FILE_ID);
                String url = map.get(KEY_URL);

                JSONObject jsonReq = new JSONObject();
                jsonReq.put(KEY_FILE_ID,fileId);
                boolean flag = false;
                try{
                    if (map.containsKey(KEY_URL)) {
                        String postResp = "";
                        if (isremote == 1){
                            postResp = HttpClientUtil.post(url, jsonReq.toJSONString());
                        }else {
                            postResp = "{\"isSuccess\":true}";
                        }
                        log.info("AsyncService.httpStartFile result deviceId:{},req:{},postResp:{}", deviceId, jsonReq.toJSONString(), postResp);
                        JSONObject jsonObject = JSONObject.parseObject(postResp);
                        if (jsonObject != null) {
                            Object success = jsonObject.get(KEY_IS_SUCCESS);
                            if (success != null && success instanceof Boolean) {
                                if ((Boolean) success) {
                                    flag = true;
                                }
                            }
                        }
                    }
                }catch (Exception e){
                    log.warn("httpStartFile.HttpClientUtil Exception:{},deviceId:{},req:{}",e.getMessage(),deviceId,jsonReq.toJSONString());
                }
                if (flag){
                    successIds.add(deviceId);
                }else {
                    failIds.add(deviceId);
                }

            }
        }catch (Exception e){
            log.warn("AsyncService.httpStartFile Exception:{}",e.getMessage());
        }
        result.put(KEY_SUCCESS_IDS,successIds);
        result.put(KEY_FAIL_IDS,failIds);
        return new AsyncResult<>(result);
    }

    @Async("taskExecutor")
    @Retryable(value = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 1000,multiplier = 1.5))
    public void httpPush(Map<String, String> map) throws Exception{
        String deviceId = map.get(KEY_DEVICE_ID);
        String url = map.get(KEY_URL);
        String data = map.get(KEY_DATA);
        String postResp = HttpClientUtil.post(url,data);
        log.info("AsyncService.httpPush url:{}, deviceId:{} , req:{}, postResp:{}",url,deviceId, data,postResp);
    }

    @Recover
    public void recover(Exception e,Map<String, String> map) {
        String deviceId = map.get(KEY_DEVICE_ID);
        log.warn("httpPush.retry error  deviceId:{} Exception:{} ",deviceId,e.getMessage());
    }

    /**
     *
     * @param url url后缀路径
     * @param versionId 版本文件id
     * @param ipmap 设备id ip 映射map
     * @throws Exception
     */
    @Async("taskExecutor")
    public void httpPushLoadFile(String url, String versionId, Map<String,String>ipmap, CountDownLatch countDownLatch) {
        try {
            NmplVersionInfo nmplVersionInfo = nmplVersionInfoMapper.selectByPrimaryKey(Long.valueOf(versionId));
            Map<String,String> map = new HashMap<>();
            File file = new File(nmplVersionInfo.getFilePath()+File.separator+nmplVersionInfo.getFileName());
            String hashCode = FileHahUtil.md5HashCode(new FileInputStream(file));
            map.put("uploadPath",nmplVersionInfo.getFilePath());
            map.put("fileName",nmplVersionInfo.getFileName());
            map.put("checkCode",hashCode);
            Set<String> set = ipmap.keySet();
            for (String s : set) {
                String httpUrl = "http://"+ipmap.get(s)+":"+url;
                Map<String,String> fileMap = new HashMap<>();
                fileMap.put("file",nmplVersionInfo.getFilePath()+File.separator+nmplVersionInfo.getFileName());
                String post = HttpClientUtil.sendPost(httpUrl, null, map, fileMap, "UTF-8", "UTF-8");
                JSONObject jsonObject = JSONObject.parseObject(post);
                if (jsonObject != null) {
                    Object success = jsonObject.get(KEY_SUCCESS);
                    if (success != null && success instanceof Boolean) {
                        if ((Boolean) success) {
                            updateDeviceLoadStatus(nmplVersionInfo,s);
                        }
                    }
                }
            }
            countDownLatch.countDown();
        }catch (Exception e){
            countDownLatch.countDown();
            log.error(e.getMessage());
        }
    }


    @Async("taskExecutor")
    public <T> Future<Map<String,Boolean>>httpUpdateVersionStatus(String url,Map<String,String> ipmap,Map<String,Long> versionMap,String updateStatus ){
        Map<String,Boolean> result = new HashMap<>();
        Set<String> set = ipmap.keySet();
        for (String s : set) {
            try {
                Long versionId = versionMap.get(s);
                NmplVersionInfo nmplVersionInfo = nmplVersionInfoMapper.selectByPrimaryKey(versionId);
                Map<String, String> data = new HashMap<>();
                data.put("uploadPath", nmplVersionInfo.getFilePath());
                data.put("fileName", nmplVersionInfo.getFileName());
                String httpUrl = "http://" + ipmap.get(s) + ":" + url;
                String post = HttpClientUtil.post(httpUrl, JSON.toJSONString(data));
                JSONObject jsonObject = JSONObject.parseObject(post);
                if (jsonObject != null) {
                    Object success = jsonObject.get(KEY_SUCCESS);
                    if (success != null && success instanceof Boolean) {
                        if ((Boolean) success) {
                            updateDeviceRunStatus(nmplVersionInfo,updateStatus,s);
                            result.put(s, true);
                        }else {
                            result.put(s, false);
                        }
                    }else {
                        result.put(s,false);
                    }
                }
            } catch (Exception e) {
                result.put(s, false);
                log.error(e.getMessage());
            }
        }
        return new AsyncResult<>(result);
    }


    /**
     *
     * @param nmplVersionInfo 版本信息
     * @param updateStatus 更新的设备运行状态
     * @param deviceId 设备id
     */
    private void updateDeviceRunStatus(NmplVersionInfo nmplVersionInfo,String updateStatus,String deviceId){
        //根据设备类别更新表
        if (nmplVersionInfo.getSystemType().equals(SYSTEM_QKC)) {
            NmplDeviceExample nmplDeviceExample = new NmplDeviceExample();
            nmplDeviceExample.createCriteria().andDeviceIdEqualTo(deviceId);
            NmplDevice nmplDevice = nmplDeviceMapper.selectByExample(nmplDeviceExample).get(0);
            nmplDevice.setRunVersionStatus(updateStatus);
            if (updateStatus.equals(VERSION_INIT_STATUS)) {
                nmplDevice.setRunVersionNo(null);
                nmplDevice.setRunFileName(null);
                nmplDevice.setRunVersionId(null);
            } else {
                nmplDevice.setRunVersionNo(nmplVersionInfo.getVersionNo());
                nmplDevice.setRunFileName(nmplVersionInfo.getFileName());
                nmplDevice.setRunVersionId(nmplVersionInfo.getId());
            }
            nmplDevice.setRunVersionOperTime(new Date());
            nmplDeviceMapper.updateByPrimaryKey(nmplDevice);
        } else {
            NmplBaseStationExample nmplBaseStationExample = new NmplBaseStationExample();
            nmplBaseStationExample.createCriteria().andStationIdEqualTo(deviceId);
            NmplBaseStation nmplBaseStation = nmplBaseStationMapper.selectByExample(nmplBaseStationExample).get(0);
            nmplBaseStation.setRunVersionStatus(updateStatus);
            if (updateStatus.equals(VERSION_INIT_STATUS)) {
                nmplBaseStation.setRunVersionNo(null);
                nmplBaseStation.setRunFileName(null);
                nmplBaseStation.setRunVersionId(null);
            } else {
                nmplBaseStation.setRunVersionNo(nmplVersionInfo.getVersionNo());
                nmplBaseStation.setRunFileName(nmplVersionInfo.getFileName());
                nmplBaseStation.setRunVersionId(nmplVersionInfo.getId());
            }
            nmplBaseStation.setRunVersionOperTime(new Date());
            nmplBaseStationMapper.updateByPrimaryKey(nmplBaseStation);
        }
    }


    /**
     *
     * @param nmplVersionInfo 版本信息
     * @param deviceId 设备id
     */
    private void updateDeviceLoadStatus(NmplVersionInfo nmplVersionInfo,String deviceId){
        //根据设备类别更新表
        if(nmplVersionInfo.getSystemType().equals(SYSTEM_QKC)){
            NmplDevice nmplDevice = new NmplDevice();
            nmplDevice.setDeviceId(deviceId);
            nmplDevice.setLoadVersionId(nmplVersionInfo.getId());
            nmplDevice.setLoadVersionNo(nmplVersionInfo.getVersionNo());
            nmplDevice.setLoadVersionOperTime(new Date());
            nmplDevice.setLoadFileName(nmplVersionInfo.getFileName());
            NmplDeviceExample nmplDeviceExample = new NmplDeviceExample();
            nmplDeviceExample.createCriteria().andDeviceIdEqualTo(deviceId);
            nmplDeviceMapper.updateByExampleSelective(nmplDevice,nmplDeviceExample);
        }else {
            NmplBaseStation nmplBaseStation = new NmplBaseStation();
            nmplBaseStation.setStationId(deviceId);
            nmplBaseStation.setLoadVersionId(nmplVersionInfo.getId());
            nmplBaseStation.setLoadVersionNo(nmplVersionInfo.getVersionNo());
            nmplBaseStation.setLoadVersionOperTime(new Date());
            nmplBaseStation.setLoadFileName(nmplVersionInfo.getFileName());
            NmplBaseStationExample nmplBaseStationExample = new NmplBaseStationExample();
            nmplBaseStationExample.createCriteria().andStationIdEqualTo(deviceId);
            nmplBaseStationMapper.updateByExampleSelective(nmplBaseStation,nmplBaseStationExample);
        }
    }

}
