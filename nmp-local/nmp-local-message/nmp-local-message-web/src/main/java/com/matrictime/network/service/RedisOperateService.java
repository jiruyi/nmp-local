package com.matrictime.network.service;


import com.matrictime.network.constant.DataConstants;
import com.matrictime.network.request.SmsCodeRequest;
import com.matrictime.network.util.AesEncryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.concurrent.TimeUnit;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project microservicecloud-jzsg
 * @date 2021/9/10 0010 15:49
 * @desc
 */
@Slf4j
@Service
@PropertySource(value = "classpath:/businessConfig.properties",encoding = "UTF-8")
public class RedisOperateService {

    @Value("${health.deadline.time}")
    private Long healthDeadlineTime;

    @Value("${sms.code.deadline.time}")
    private Long smsCodeDeadlineTime;

    @Value("${phone.sms.count.deadline.time}")
    private Long phoneSmsCountDeadlineTime;

    @Value("${phone.sms.count}")
    private int phoneSmsCount;

    @Autowired
    private RedisTemplate redisTemplate;





    /**
     * @title smsCodeSaveRedis
     * @param [key, code]
     * @return boolean
     * @description  短信验证码有效期限
     * @author jiruyi
     * @create 2021/9/14 0014 17:09
     */
    public boolean smsCodeSaveRedis(String key,String code ){
        redisTemplate.opsForValue().set(key, code,smsCodeDeadlineTime, TimeUnit.SECONDS);
        return true;
    }

    /**
     * @title phoneSmsCountSaveRedis
     * @param [phone]
     * @return boolean
     * @description  短信频次限制
     * @author jiruyi
     * @create 2021/9/14 0014 17:29
     */
    public boolean phoneSmsCountSaveRedis(String phone){
        if(redisTemplate.hasKey(DataConstants.PHONE_SMS_DAY_COUNT+phone)){
            redisTemplate.opsForValue().increment(DataConstants.PHONE_SMS_DAY_COUNT+phone);
        }else {
            redisTemplate.opsForValue().set(DataConstants.PHONE_SMS_DAY_COUNT+phone, NumberUtils.INTEGER_ONE,
                    phoneSmsCountDeadlineTime, TimeUnit.SECONDS);
        }
        return true;
    }


    /**
     * @title getPhoneSmsCountLimit
     * @param [phone]
     * @return boolean
     * @description
     * @author jiruyi
     * @create 2021/9/14 0014 17:56
     */
    public boolean getPhoneSmsCountLimit(String phone){
        try {
            Object countObj = redisTemplate.opsForValue().get(DataConstants.PHONE_SMS_DAY_COUNT+phone);
            if(ObjectUtils.isEmpty(countObj)){
                return true;
            }
            if(phoneSmsCount >= (int)countObj){
                return true;
            }
        }catch (Exception e){
            log.error("获取手机号redis短信次数异常：{}",e.getMessage());
        }
        return false;
    }

    /**
     * @title verifySmsCode
     * @param [smsCodeRequest]
     * @return boolean
     * @description  验证码 验证
     * @author jiruyi
     * @create 2021/10/25 0025 11:08
     */
    public boolean verifySmsCode(SmsCodeRequest smsCodeRequest){
        try {
            smsCodeRequest.setSmsCode(AesEncryptUtil.aesDecrypt(smsCodeRequest.getSmsCode()));
        } catch (Exception e) {
            log.error("verifySmsCode AesEncryptUtil.aesDecrypt code:{},exception:{}",smsCodeRequest.getSmsCode()
                    ,e.getMessage());
        }
        Object countObj = redisTemplate.opsForValue().get(smsCodeRequest.getBizCode().trim()
                +DataConstants.KEY_SPLIT+smsCodeRequest.getPhoneNo());
        if(ObjectUtils.isEmpty(countObj)){
            return false;
        }
        //成功
        if(countObj.toString().equals(smsCodeRequest.getSmsCode())){
            //删除验证码
            redisTemplate.delete(smsCodeRequest.getBizCode().trim()+DataConstants.KEY_SPLIT+smsCodeRequest.getPhoneNo());
            return true;
        }
        return false;
    }
}
