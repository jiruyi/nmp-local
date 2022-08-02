package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.api.modelVo.PushUserVo;
import com.matrictime.network.api.modelVo.WsResultVo;
import com.matrictime.network.api.modelVo.WsSendVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import static com.matrictime.network.constant.DataConstants.*;


@Service
@Slf4j
public class AsyncService {

    @Async("taskExecutor")
    public Future<Map<String,List<String>>> pushOnlineUser(String input) {

        Map<String,List<String>> result = new HashMap<>();
        List<String> successIds = new ArrayList<>();
        List<String> failIds = new ArrayList<>();
        String userId = "";
        try {
            JSONObject jsonObject = JSONObject.parseObject(input);
            if (jsonObject.containsKey("pushOnlineUsers") && jsonObject.containsKey("pushInfo")){
                List<String> pushOnlineUsers = jsonObject.getObject("pushOnlineUsers",List.class);
                PushUserVo pushUserVo = jsonObject.getObject("pushInfo",PushUserVo.class);
                for (String sendObject : pushOnlineUsers){
                    userId = sendObject;
                    WsResultVo wsResultVo = new WsResultVo();
                    WsSendVo wsSendVo = new WsSendVo();
                    wsSendVo.setData(JSONObject.toJSONString(pushUserVo));
                    wsSendVo.setFrom(SYSTEM_UC);
                    wsSendVo.setBusinessCode("15");
                    wsResultVo.setResult(JSONObject.toJSONString(wsSendVo));
                    log.info("AsyncService.pushOnlineUser sendObject:{},pushUserVo:{}",sendObject,JSONObject.toJSONString(pushUserVo));
                    boolean flag = false;
                    try{
                        WebSocketServer webSocketServer = WebSocketServer.getWebSocketMap().get(sendObject);
                        if(webSocketServer != null){
                            webSocketServer.sendMessage(JSONObject.toJSONString(wsResultVo));
                        }
                    }catch (Exception e){
                        log.warn("httpSyncConfig.HttpClientUtil Exception:{},sendObject:{},pushUserVo:{}",e.getMessage(),sendObject,JSONObject.toJSONString(pushUserVo));
                    }
                    if (flag){
                        successIds.add(userId);
                    }else {
                        failIds.add(userId);
                    }
                }
            }
        }catch (Exception e){
            log.warn("AsyncService.pushOnlineUser Exception:{}",e.getMessage());
        }
        result.put(KEY_SUCCESS_IDS,successIds);
        result.put(KEY_FAIL_IDS,failIds);
        return new AsyncResult<>(result);
    }



}
