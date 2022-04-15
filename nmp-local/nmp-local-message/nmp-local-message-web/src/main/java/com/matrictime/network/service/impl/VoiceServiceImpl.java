package com.matrictime.network.service.impl;//package src.main.java.com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.dyvmsapi20170525.models.SingleCallByTtsRequest;
import com.aliyun.teaopenapi.models.Config;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.dao.model.NmplUser;
import com.matrictime.network.domain.NmplUserDomainService;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.exception.SystemException;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.VoiceCallRequest;
import com.matrictime.network.service.VoiceService;
import com.matrictime.network.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project microservicecloud-jzsg
 * @date 2021/11/22 0022 15:00
 * @desc
 */
@Service
@Slf4j
@PropertySource(value = "classpath:/businessConfig.properties",encoding = "UTF-8")
public class VoiceServiceImpl extends SystemBaseService implements VoiceService {

    @Value("${sms.accesskey.id}")
    private   String accessKeyId;

    @Value("${sms.accesskey.secret}")
    private  String accessKeySecret;

    @Autowired
    private NmplUserDomainService userDomainService;

    @Value("${vms.request.url}")
    private  String vmsUrl;
    /**
     * @title voiceCall
     * @param []
     * @return com.matrictime.network.model.Result
     * @description
     * @author jiruyi
     * @create 2021/11/8 0008 17:32
     */
    @Override
    public Result voiceCall(VoiceCallRequest voiceCallRequest) {
        Result result =  new Result();
        try {
            //为校验
            if(ObjectUtils.isEmpty(voiceCallRequest) || ObjectUtils.isEmpty(voiceCallRequest.getCalledNumber())
                    || ObjectUtils.isEmpty(voiceCallRequest.getTtsCode())){
                throw new SystemException(ErrorMessageContants.PHONE_IS_NOT_EXIST);
            }
            //手机号本地校验
            if(!phoneIsExist(voiceCallRequest.getCalledNumber())){
                throw new SystemException(ErrorMessageContants.PHONE_IS_NOT_EXIST);
            }
            //1.构建发送参数
            com.aliyun.dyvmsapi20170525.Client client = createVmsClient();
            SingleCallByTtsRequest singleCallByTtsRequest = new SingleCallByTtsRequest();

            //json 校验
            if(!isJson(voiceCallRequest.getTtsParam())){
                throw new SystemException(ErrorMessageContants.JSON_ERROR_FAIL_MSG);
            }
            BeanUtils.copyProperties(voiceCallRequest,singleCallByTtsRequest);
            //发起呼叫
            client.singleCallByTts(singleCallByTtsRequest);

            log.info("手机号:{}在:{}时间发起语音呼叫",voiceCallRequest.getCalledNumber(),
                    DateUtils.formatDateToString(new Date()));
        } catch (Exception e) {
            log.error("手机号:{}在:{}时间发起语音呼叫失败:{}",voiceCallRequest.getCalledNumber(),
                    DateUtils.formatDateToString(new Date()),e.getMessage());
            result = failResult(e);
        }
        return  result;
    }

    /**
      * @title voiceCallBactch
      * @param [voiceCallRequest]
      * @return com.matrictime.network.model.Result
      * @description  批量语音 给所有的管理员和运维打语音
      * @author jiruyi
      * @create 2021/11/25 0025 17:42
      */
    @Override
    public Result voiceCallBactch(VoiceCallRequest voiceCallRequest) {
        Result result =  new Result();
        try {
            List<String> phoneList = voiceCallRequest.getCalledNumberList();
            if(CollectionUtils.isEmpty(phoneList)){
                return result;
            }
            for(String phone : phoneList){
                VoiceCallRequest voiceCall =  VoiceCallRequest.builder()
                        .calledNumber(phone)
                        .ttsCode(voiceCallRequest.getTtsCode())
                        .ttsParam(voiceCallRequest.getTtsParam()).build();
                voiceCall(voiceCall);
            }
        }catch (Exception e){
            log.error("手机号:{}在:{}时间发起语音呼叫失败:{}",voiceCallRequest.getCalledNumber(),
                    DateUtils.formatDateToString(new Date()),e.getMessage());
            result = failResult(e);
        }
        return  result;
    }

    /**
     * 使用AK&SK初始化账号Client
     * @return Client
     * @throws Exception
     */
    public  com.aliyun.dyvmsapi20170525.Client createVmsClient() throws Exception {
        Config config = new Config()
                // 您的AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 您的AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        // 访问的域名
        config.endpoint = vmsUrl;
        return new com.aliyun.dyvmsapi20170525.Client(config);
    }
    /**
     * @title phoneIsExist
     * @param [phone]
     * @return boolean
     * @description
     * @author jiruyi
     * @create 2021/9/15 0015 10:15
     */
    private boolean phoneIsExist(String phone){
        int count = userDomainService.getUserCountByPhone(phone);
        return count > 0 ? true:false;
    }


    /**
     * @title isJson
     * @param [str]
     * @return boolean
     * @description
     * @author jiruyi
     * @create 2021/11/8 0008 18:01
     */
    public boolean isJson(String str){
        if(StringUtils.isEmpty(str)){
            return true;
        }
        try {
            JSONObject.parse(str);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
