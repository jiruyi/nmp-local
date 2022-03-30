package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.model.Result;
import com.matrictime.network.util.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

@Service
@Slf4j
public class AsyncService{

    @Async("taskExecutor")
    public Future<Map<String,List<String>>> httpSyncConfig(List<Map<String, String>> list) {

        Map<String,List<String>> result = new HashMap<>();
        List<String> successIds = new ArrayList<>(list.size());
        List<String> failIds = new ArrayList<>();
        String deviceId = "";
        try {
            for (Map<String,String> map : list){
                Map<String ,String> httpParam = new HashMap<>(8);
                deviceId = httpParam.get("deviceId");
                httpParam.put("configCode",map.get("configCode"));
                httpParam.put("configValue",map.get("configValue"));
                httpParam.put("unit",map.get("unit"));
                String post = "";
                try{
                    post = HttpClientUtil.post(map.get("ip"), map.get("port"), "path", httpParam);
                }catch (Exception e){
                    post = "true";
                }
                if (post == "true"){
                    successIds.add(deviceId);
                }else {
                    failIds.add(deviceId);
                }
            }
        }catch (Exception e){
            log.warn("AsyncService.httpSyncConfig Exception:{}",e.getMessage());
        }
        result.put("successIds",successIds);
        result.put("failIds",failIds);
        return new AsyncResult<>(result);
    }
}
