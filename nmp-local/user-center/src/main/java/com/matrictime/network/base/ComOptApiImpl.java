package com.matrictime.network.base;

import com.alibaba.fastjson.JSONObject;
import com.jzsg.bussiness.JServiceImpl;
import com.jzsg.bussiness.model.ReqModel;
import com.jzsg.bussiness.model.ResModel;
import com.jzsg.bussiness.util.EdException;
import com.jzsg.bussiness.ws.ComOptApi;
import com.matrictime.network.constant.DataConstants;
import com.matrictime.network.util.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class ComOptApiImpl implements ComOptApi {

    @Override
    public void optImpl(Object paramObject) {
        if (paramObject != null) {
            ReqModel reqModel = JSONObject.parseObject(paramObject.toString(), ReqModel.class);
            log.info("密区接收信息ComOptApiImpl.optImpl paramObject:{}", JSONObject.toJSONString(reqModel));

            Object param = reqModel.getParam();
            if (param != null && param instanceof String) {
                JSONObject jsonObject = JSONObject.parseObject((String) param);
                String url = jsonObject.getString(DataConstants.MAP_KEY_URL);
                jsonObject.put(DataConstants.MAP_KEY_UUID, reqModel.getUuid());
                jsonObject.remove(DataConstants.MAP_KEY_URL);
                try {
                    log.info("密区处理接收信息HttpClientUtil.post url:{},paramObject:{}",url, jsonObject.toJSONString());
                    String post = HttpClientUtil.post(url, jsonObject.toJSONString());
                    log.info("密区处理接收信息结果HttpClientUtil.post post:{}",post);
                    ResModel resModel = new ResModel();
                    resModel.setUuid(reqModel.getUuid());
                    resModel.setReturnValue(post);
                    JServiceImpl.asynSendMsg(JSONObject.toJSONString(resModel));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (EdException e) {
                    e.printStackTrace();
                }
            }
        }else {
            log.error("ComOptApiImpl paramObject is null or not String");
        }
    }
}
