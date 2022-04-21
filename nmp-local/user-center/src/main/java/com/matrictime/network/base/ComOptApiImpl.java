package com.matrictime.network.base;

import com.alibaba.fastjson.JSONObject;
import com.jzsg.bussiness.ws.ComOptApi;
import com.matrictime.network.constant.DataConstants;
import com.matrictime.network.util.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class ComOptApiImpl implements ComOptApi {

    @Override
    public void optImpl(Object paramObject) {
        if (paramObject != null && paramObject instanceof String) {
            JSONObject jsonObject = JSONObject.parseObject((String) paramObject);
            log.info("ComOptApiImpl.optImpl paramObject:{}", jsonObject.toJSONString());

            String url = jsonObject.getString(DataConstants.MAP_KEY_URL);
            jsonObject.remove(DataConstants.MAP_KEY_URL);
            try {
                HttpClientUtil.post(url, jsonObject.toJSONString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            log.error("ComOptApiImpl paramObject is null or not String");
        }
    }
}
