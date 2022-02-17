package com.matrictime.network.controller;

import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.SmsCodeRequest;
import com.matrictime.network.request.VoiceCallRequest;
import com.matrictime.network.service.RedisOperateService;
import com.matrictime.network.service.SmsService;
import com.matrictime.network.service.VoiceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project microservicecloud-jzsg
 * @date 2021/9/15 0015 10:03
 * @desc 短信发送
 */
@RequestMapping(value = "/sms")
@Api(value = "短信相关",tags = "短信相关")
@RestController
@Slf4j
public class SmsCodeController {

    @Autowired
    private SmsService smsService;

    @Autowired
    private VoiceService voiceService;

    @Autowired
    private RedisOperateService redisOperateService;

    /**
     * @title smsSend
     * @param [smsCodeRequest]
     * @return com.matrictime.network.model.Result
     * @description
     * @author jiruyiS
     * @create 2021/9/15 0015 10:09
     */
    @ApiOperation(value = "短信发送",notes = "业务类型： 登录：login_sms_code 路由：route_sms_code")
    @RequestMapping (value = "/send",method = RequestMethod.POST)
    public Result smsSend(@RequestBody SmsCodeRequest smsCodeRequest){
        try {
            /**1.0 参数校验**/
            if(ObjectUtils.isEmpty(smsCodeRequest) || ObjectUtils.isEmpty(smsCodeRequest.getPhoneNo())
                    || ObjectUtils.isEmpty(smsCodeRequest.getBizCode())){
                return new Result(false, ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
            return  smsService.sendSmsCode(smsCodeRequest.getPhoneNo(),smsCodeRequest.getBizCode());
        }catch (Exception e){
            log.error("smsSend exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * @title
     * @param [smsCodeRequest]
     * @return com.matrictime.network.model.Result
     * @description  短信验证码校验
     * @author jiruyi
     * @create 2021/9/15 0015 11:23
     */
    @ApiOperation(value = "短信验证码校验",notes = "业务类型： 登录：login_sms_code 路由：route_sms_code")
    @RequestMapping (value = "/verify",method = RequestMethod.POST)
    public Result smsVerify(@RequestBody SmsCodeRequest smsCodeRequest){
        try {
            /**1.0 参数校验**/
            if(ObjectUtils.isEmpty(smsCodeRequest) || ObjectUtils.isEmpty(smsCodeRequest.getPhoneNo())
                    || ObjectUtils.isEmpty(smsCodeRequest.getBizCode())
                    || ObjectUtils.isEmpty(smsCodeRequest.getSmsCode())){
                return new Result(false, ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
            if(redisOperateService.verifySmsCode(smsCodeRequest)){
                return new Result();
            }else {
                return new Result(false,ErrorMessageContants.SMS_CODE_ERROR_MSG);
            }
        }catch (Exception e){
            log.error("smsSend exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }


    /**
     * @title voiceCall
     * @param [voiceCallRequest]
     * @return com.matrictime.network.model.Result
     * @description
     * @author jiruyi
     * @create 2021/11/15 0015 16:48
     */
    @ApiOperation(value = "语音通知",notes = "语音呼叫")
    @RequestMapping (value = "/voice",method = RequestMethod.POST)
    public Result voiceCall(@RequestBody VoiceCallRequest voiceCallRequest){
        try {
            /**1.0 参数校验**/
            if(ObjectUtils.isEmpty(voiceCallRequest) || ObjectUtils.isEmpty(voiceCallRequest.getCalledNumber())
                    || ObjectUtils.isEmpty(voiceCallRequest.getTtsCode())){
                return new Result(false, ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
            return  voiceService.voiceCall(voiceCallRequest);
        }catch (Exception e){
            log.error("voice  call exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }
}
