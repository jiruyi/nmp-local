package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.constant.DataConstants;
import com.matrictime.network.dao.mapper.NmplFileDeviceRelMapper;
import com.matrictime.network.dao.mapper.NmplSignalIoMapper;
import com.matrictime.network.dao.model.NmplFileDeviceRel;
import com.matrictime.network.dao.model.NmplFileDeviceRelExample;
import com.matrictime.network.dao.model.NmplSignalIo;
import com.matrictime.network.dao.model.NmplSignalIoExample;
import com.matrictime.network.util.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
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


    @Async("taskExecutor")
    public Future<Map<String,List<String>>> httpSyncConfig(List<Map<String, String>> list) {

        Map<String,List<String>> result = new HashMap<>();
        List<String> successIds = new ArrayList<>(list.size());
        List<String> failIds = new ArrayList<>();
        String deviceId = "";
        try {
            for (Map<String,String> map : list){
                deviceId = map.get(KEY_DEVICE_ID);
                String configCode = map.get(KEY_CONFIG_CODE);
                JSONObject jsonReq = new JSONObject();
                jsonReq.put(KEY_CONFIG_CODE,configCode);
                jsonReq.put(KEY_CONFIG_VALUE,map.get(KEY_CONFIG_VALUE));
                jsonReq.put(KEY_UNIT,map.get(KEY_UNIT));
                boolean flag = false;
                try{
                    // TODO: 2022/3/31 返回值暂时写死，配置同步需要和站点联调获取返回值
//                    String post = HttpClientUtil.post(map.get(KEY_URL), jsonReq.toJSONString());
                    String post = "{\"isSuccess\":false}";
                    log.info("AsyncService.httpSyncConfig result deviceId:{},req:{},post:{}",deviceId,jsonReq.toJSONString(),post);
                    JSONObject jsonObject = JSONObject.parseObject(post);
                    if (jsonObject != null && jsonObject.get(KEY_IS_SUCCESS) instanceof Boolean){
                        flag = (Boolean) jsonObject.get(KEY_IS_SUCCESS);
                    }
                }catch (Exception e){
                    log.warn("httpSyncConfig.HttpClientUtil Exception:{},deviceId:{},req:{}",e.getMessage(),deviceId,jsonReq.toJSONString());
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
                deviceId = map.get(KEY_DEVICE_ID);
                String userId = map.get(KEY_USER_ID);
                String ioType = map.get(KEY_IO_TYPE);
                String url = map.get(KEY_URL);
                JSONObject jsonReq = new JSONObject();
                jsonReq.put(KEY_IO_TYPE,ioType);
                boolean flag = false;
                try{
                    // TODO: 2022/3/31 返回值暂时写死，配置同步需要和站点联调获取返回值
//                    String postResp = HttpClientUtil.post(url, jsonReq.toJSONString());
                    String postResp = "{\"isSuccess\":true}";
                    log.info("AsyncService.httpSignalIo result deviceId:{},userId:{},req:{},postResp:{}",deviceId,userId,jsonReq.toJSONString(),postResp);
                    JSONObject jsonObject = JSONObject.parseObject(postResp);
                    if (jsonObject != null){
                        Object success = jsonObject.get(KEY_IS_SUCCESS);
                        if (success != null && success instanceof Boolean){
                            if ((Boolean)success){
                                flag = true;
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
                    // TODO: 2022/3/31 返回值暂时写死，配置同步需要和站点联调获取返回值
//                    String postResp = HttpClientUtil.post(url, jsonReq.toJSONString());
                    String postResp = "{\"isSuccess\":true}";
                    log.info("AsyncService.httpPushFile result url:{},deviceId:{},req:{},postResp:{}",url,deviceId,jsonReq.toJSONString(),postResp);
                    JSONObject jsonObject = JSONObject.parseObject(postResp);
                    if (jsonObject != null){
                        Object success = jsonObject.get(KEY_IS_SUCCESS);
                        if (success != null && success instanceof Boolean){
                            if ((Boolean)success){
                                flag = true;
                                NmplFileDeviceRelExample example = new NmplFileDeviceRelExample();
                                example.createCriteria().andDeviceIdEqualTo(deviceId).andFileIdEqualTo(Long.parseLong(map.get(KEY_FILE_ID)));
                                List<NmplFileDeviceRel> rels = nmplFileDeviceRelMapper.selectByExample(example);
                                if (CollectionUtils.isEmpty(rels)){
                                    NmplFileDeviceRel nmplFileDeviceRel = new NmplFileDeviceRel();
                                    nmplFileDeviceRel.setFileId(Long.parseLong(map.get(KEY_FILE_ID)));
                                    nmplFileDeviceRel.setDeviceId(deviceId);
                                    nmplFileDeviceRelMapper.insertSelective(nmplFileDeviceRel);
                                }else if (!rels.get(0).getIsDelete()){
                                    NmplFileDeviceRel nmplFileDeviceRel = new NmplFileDeviceRel();
                                    nmplFileDeviceRel.setId(rels.get(0).getId());
                                    nmplFileDeviceRel.setIsDelete(IS_EXIST);
                                    nmplFileDeviceRel.setUpdateTime(new Date());
                                    nmplFileDeviceRelMapper.updateByPrimaryKeySelective(nmplFileDeviceRel);
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
                String deviceId = map.get(KEY_DEVICE_ID);
                String fileId = map.get(KEY_FILE_ID);
                String url = map.get(KEY_URL);

                JSONObject jsonReq = new JSONObject();
                jsonReq.put(KEY_FILE_ID,fileId);
                boolean flag = false;
                try{
                    // TODO: 2022/3/31 返回值暂时写死，配置同步需要和站点联调获取返回值
//                    String postResp = HttpClientUtil.post(url, jsonReq.toJSONString());
                    String postResp = "{\"isSuccess\":true}";
                    log.info("AsyncService.httpStartFile result deviceId:{},req:{},postResp:{}",deviceId,jsonReq.toJSONString(),postResp);
                    JSONObject jsonObject = JSONObject.parseObject(postResp);
                    if (jsonObject != null){
                        Object success = jsonObject.get(KEY_IS_SUCCESS);
                        if (success != null && success instanceof Boolean){
                            if ((Boolean)success){
                                flag = true;
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
}
