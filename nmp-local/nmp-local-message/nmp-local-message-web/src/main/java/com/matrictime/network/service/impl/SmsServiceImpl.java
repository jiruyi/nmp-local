package com.matrictime.network.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.teaopenapi.models.Config;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.constant.DataConstants;
import com.matrictime.network.dao.model.NmplSmsDetail;
import com.matrictime.network.domain.NmplSmsDetailDomainService;
import com.matrictime.network.domain.NmplUserDomainService;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.exception.SystemException;
import com.matrictime.network.model.Result;
import com.matrictime.network.service.RedisOperateService;
import com.matrictime.network.service.SmsService;
import com.matrictime.network.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project microservicecloud-jzsg
 * @date 2021/9/14 0014 15:21
 * @desc 发送短信
 */
@Service
@Slf4j
@PropertySource(value = "classpath:/businessConfig.properties",encoding = "UTF-8")
public class SmsServiceImpl extends SystemBaseService implements SmsService {

    @Value("${sms.accesskey.id}")
    private   String accessKeyId;

    @Value("${sms.accesskey.secret}")
    private  String accessKeySecret;

    @Value("${sms.request.url}")
    private  String smsUrl;


    @Value("${sign.name}")
    private  String signName;

    @Value("${template.code}")
    private  String templateCode;

    @Autowired
    private NmplSmsDetailDomainService smsDetailDomainService;

    @Autowired
    private RedisOperateService redisOperateService;

    @Autowired
    private NmplUserDomainService userDomainService;
    /**
     * @title createClient
     * @param []
     * @return com.aliyun.dysmsapi20170525.Client
     * @description
     * @author jiruyi
     * @create 2021/9/14 0014 15:30
     */
    public  Client createSmsClient() throws Exception {
        Config config = new Config()
                //AccessKey ID
                .setAccessKeyId(accessKeyId)
                //AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = smsUrl;
        return new Client(config);
    }

    /**
     * @title sendSmsCode
     * @param [phoneNumber]
     * @return void
     * @description
     * @author jiruyi
     * @create 2021/9/14 0014 15:50
     */
    @Override
    public Result sendSmsCode(String phoneNumber, String bizCode) throws SystemException {
        Result result =  new Result();
        try {
            //手机短信限流本地校验
            if(!redisOperateService.getPhoneSmsCountLimit(phoneNumber)){
                throw new SystemException(ErrorMessageContants.PHONE_SMS_COUNT_LIMIT);
            }
            //手机号本地校验
            if(!phoneIsExist(phoneNumber)){
                throw new SystemException(ErrorMessageContants.PHONE_IS_NOT_EXIST);
            }
            //1.构建发送参数
            Client client = createSmsClient();
            SendSmsRequest sendSmsRequest = new SendSmsRequest();
            sendSmsRequest.setPhoneNumbers(phoneNumber);
            sendSmsRequest.setSignName(signName);
            sendSmsRequest.setTemplateCode(templateCode);
            JSONObject jsonObject = new JSONObject();
            String code = getSixCode(6);
            jsonObject.put("code",code);
            //2.调用发送短信
            sendSmsRequest.setTemplateParam( jsonObject.toJSONString());
            client.sendSms(sendSmsRequest);
            log.info("手机号:{}在:{}时间发送的短信验证码是:{}",phoneNumber,
                    DateUtils.formatDateToString(new Date()),jsonObject.toJSONString());
            //3.持久化
            smsPersist(phoneNumber,code);
            //4.0 验证码存入redis用于验证
            redisOperateService.smsCodeSaveRedis(bizCode.trim()+ DataConstants.KEY_SPLIT+phoneNumber,code);
            //5.0 发送次数放入redis限流
            redisOperateService.phoneSmsCountSaveRedis(phoneNumber);
        } catch (Exception e) {
            log.error("手机号:{}在:{}时间发送短信验证码失败:{}",phoneNumber,
                    DateUtils.formatDateToString(new Date()),e.getMessage());
            result = failResult(e);
        }
        return  result;
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
     * @title smsPersist
     * @param [phone, code]
     * @return void
     * @description
     * @author jiruyi
     * @create 2021/9/14 0014 16:49
     */
    public void smsPersist(String phone,String code){
        try {
            NmplSmsDetail smsDetail = NmplSmsDetail.builder()
                    .phone(phone).smsVerificationCode(code).build();
            smsDetailDomainService.insertSmsDetail(smsDetail);
        }catch (Exception e){
            log.error("手机号：{}，验证码：{} 持久化数据库异常：{}",phone,code,e.getMessage());
        }

    }
    /**
     * @title getSixCode
     * @param [length]
     * @return java.lang.String
     * @description  获取验证码
     * @author jiruyi
     * @create 2021/9/14 0014 15:46
     */
    public static String getSixCode(int length){
        StringBuffer code = new StringBuffer();
        Random random = new Random();
        for(int i =0 ;i< length ;i++){
            int num = random.nextInt(10);
            code.append(num);
        }
        return code.toString();
    }

}
