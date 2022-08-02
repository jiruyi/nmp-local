package com.matrictime.network.controller;


import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import com.jzsg.bussiness.JServiceImpl;
import com.jzsg.bussiness.util.EdException;
import com.matrictime.network.api.request.BaseReq;
import com.matrictime.network.api.request.LoginReq;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.UcConstants;
import com.matrictime.network.base.util.ReqUtil;
import com.matrictime.network.controller.aop.MonitorRequest;
import com.matrictime.network.model.Result;
import com.matrictime.network.util.AesEncryptUtil;
import com.matrictime.network.util.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;

@RestController
@Slf4j
@RequestMapping(value = "/ext")
public class EncryptController extends SystemBaseService {

    @RequestMapping(value = "/encrypt")
    public Result encrypt(@RequestBody LoginReq req){

        Result result = new Result();
        try {
            ReqUtil<LoginReq> jsonUtil = new ReqUtil<>(req);
            req = jsonUtil.jsonReqToDto(req);
            log.info("EncryptController.encrypt req:{}",req.toString());
            return result;
        }catch (Exception e){
            log.error("LoginController.login exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    @RequestMapping(value = "/jzdq")
    public Result jzdq(){
        Result result = new Result();


        try {
            String url = "{http://127.0.0.1:8007/encrypt/}";
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("destination","0");
            jsonObject.put("userId","8208299496659410944");
            url = url +URLEncoder.encode(jsonObject.toJSONString(), "UTF-8");
            JSONObject object = new JSONObject();
            object.put("destination","0");
            object.put("userId","1");
            HttpClientUtil.post(url,object.toJSONString());
            return result;
        }catch (Exception e){
            log.error("LoginController.login exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    @RequestMapping(value = "/jjm")
    public Result jjm(@RequestBody BaseReq req){
        Result result = new Result();
        try {
            switch (req.getDestination()){
                case UcConstants.DESTINATION_IN:
                    String encryptMsg = "";
                    if (StringUtils.isBlank(req.getCommonKey())){
                        encryptMsg = JServiceImpl.encryptMsg(req.getCommonParam(), req.getUrl());
                    }else {
                        encryptMsg = AesEncryptUtil.aesEncrypt(req.getCommonParam());
                    }
                    result = buildResult("加密结果："+encryptMsg);
                    break;
                case UcConstants.DESTINATION_OUT:
                    String decryptMsg ="";
                    if (StringUtils.isBlank(req.getCommonKey())){
                        decryptMsg = JServiceImpl.decryptMsg(req.getEncryptParam());
                    }else {
                        decryptMsg = AesEncryptUtil.aesDecrypt(req.getEncryptParam());
                    }
                    result = buildResult(decryptMsg);
                    break;
                default:
                    break;
            }
            return result;
        }catch (Exception e){
            log.error("EncryptController.jjm exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    @RequestMapping(value = "/sendMsgTest")
    @MonitorRequest
    public Result sendMsg(@RequestBody BaseReq req){

        Result result = new Result();
        try {
            String s = "{\"destination\":\"1\",\"result\":\"000000000000000000000000000000000B0000003D01000002000000227A5E216670756E6A6075744B666E6E542B30572E3A522D20512C6B7165734F322B4E316F494A4B367472734D6A7F69517D4647403F244340413C271210111411191A151117131516111312181D7677740B067770710C4B495E5A465E50465A5F5F6E6F68170C6B6869140764656619146566671E53575C57735F52251D1E1F627B1E1F1867252F212B372E14151669641516176E3D26202228003A3D3337210C0D0E716E090A0B7664656768696A6B68696A6B0001027D7001020342130712150411172D013A3B38475C3B343548535A585E5B5B5D5A5C5D5C56594749424246412E2F28575A2B282954041D012627245B402720215C4F2021225DACDDDEDFA2F4F1E6F6CCE2DBD8D9A4BDD4D5D6A9B0BBBAB3BEB4B7BBB5BBB8BAA9A5A3A3A9A5A6CFC8C9B4EAC8B7BACBBAFFE8F4F5C4BBA3C6B8EEF8C0BEE0BF9E\"}";
            WebSocketServer webSocketServer = WebSocketServer.getWebSocketMap().get(req.getCommonKey());
            if(webSocketServer != null){
                webSocketServer.sendMessage(req.getCommonParam());
            }
        }catch (Exception e){
            result = failResult(e);
        }
        result.setSuccess(true);
        result.setResultObj("发送成功");
        return result;
    }

    @RequestMapping(value = "/onclose")
    public void onclose(@RequestBody BaseReq req){
        WebSocketServer webSocketServer = WebSocketServer.getWebSocketMap().get(req.getCommonKey());
        if(webSocketServer != null){
            webSocketServer.serverClose();
        }
    }

}
