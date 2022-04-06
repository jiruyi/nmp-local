package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.constant.DataConstants;
import com.matrictime.network.dao.mapper.NmplSignalIoMapper;
import com.matrictime.network.dao.mapper.NmplVersionFileMapper;
import com.matrictime.network.dao.model.NmplSignalIo;
import com.matrictime.network.dao.model.NmplSignalIoExample;
import com.matrictime.network.model.Result;
import com.matrictime.network.service.SignalService;
import com.matrictime.network.util.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import static com.matrictime.network.base.constant.DataConstants.*;

@Service
@Slf4j
public class AsyncService{

    @Autowired(required = false)
    private NmplSignalIoMapper nmplSignalIoMapper;


    @Async("taskExecutor")
    public Future<Map<String,List<String>>> httpSyncConfig(List<Map<String, String>> list) {

        Map<String,List<String>> result = new HashMap<>();
        List<String> successIds = new ArrayList<>(list.size());
        List<String> failIds = new ArrayList<>();
        String deviceId = "";
        try {
            for (Map<String,String> map : list){
                Map<String ,String> httpParam = new HashMap<>(8);
                deviceId = map.get(KEY_DEVICE_ID);
                String configCode = map.get(KEY_CONFIG_CODE);
                httpParam.put(KEY_CONFIG_CODE,configCode);
                httpParam.put(KEY_CONFIG_VALUE,map.get(KEY_CONFIG_VALUE));
                httpParam.put(KEY_UNIT,map.get(KEY_UNIT));
                boolean flag = false;
                try{
                    // TODO: 2022/3/31 返回值暂时写死，配置同步需要和站点联调获取返回值
//                    String post = HttpClientUtil.post(map.get(KEY_URL), httpParam);
                    String post = "{\"isSuccess\":false}";
                    log.info("AsyncService.httpSyncConfig result deviceId:{},configCode:{},post:{}",deviceId,configCode,post);
                    JSONObject jsonObject = JSONObject.parseObject(post);
                    if (jsonObject != null && jsonObject.get(KEY_IS_SUCCESS) instanceof Boolean){
                        flag = (Boolean) jsonObject.get(KEY_IS_SUCCESS);
                    }
                }catch (Exception e){
                    log.warn("httpSyncConfig.HttpClientUtil Exception:{},deviceId:{},configCode:{}",e.getMessage(),deviceId,configCode);
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
                Map<String ,String> httpParam = new HashMap<>(2);
                deviceId = map.get(KEY_DEVICE_ID);
                String userId = map.get(KEY_USER_ID);
                String ioType = map.get(KEY_IO_TYPE);
                String url = map.get(KEY_URL);
                httpParam.put(KEY_IO_TYPE,ioType);
                boolean flag = false;
                try{
                    // TODO: 2022/3/31 返回值暂时写死，配置同步需要和站点联调获取返回值
//                    String postResp = HttpClientUtil.post(url, httpParam);
                    String postResp = "{\"isSuccess\":true}";
                    log.info("AsyncService.httpSignalIo result deviceId:{},userId:{},postResp:{}",deviceId,userId,postResp);
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
                    log.warn("httpSignalIo.HttpClientUtil Exception:{},deviceId:{}",e.getMessage(),deviceId);
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
    public Future<Map<String,List<String>>> httpStartFile(List<Map<String, String>> list) {

        Map<String,List<String>> result = new HashMap<>();
        List<String> successIds = new ArrayList<>(list.size());
        List<String> failIds = new ArrayList<>();
        try {
            for (Map<String,String> map : list){
                String deviceId = map.get(KEY_DEVICE_ID);
                String fileId = map.get(KEY_FILE_ID);
                String url = map.get(KEY_URL);
                String filePath = map.get(KEY_FILE_PATH);
                boolean flag = false;
                try{
                    // TODO: 2022/3/31 返回值暂时写死，配置同步需要和站点联调获取返回值
//                    String postResp = HttpClientUtil.postForm(url, filePath);
                    String postResp = "{\"isSuccess\":true}";
                    log.info("AsyncService.httpSignalIo result deviceId:{},fileId:{},postResp:{}",deviceId,fileId,postResp);
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
                    log.warn("httpStartFile.HttpClientUtil Exception:{},deviceId:{},fileId:{}",e.getMessage(),deviceId,fileId);
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
