package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.api.request.LogoutReq;
import com.matrictime.network.api.request.PushTokenReq;
import com.matrictime.network.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import static com.matrictime.network.base.DataConfig.*;
import static com.matrictime.network.constant.DataConstants.*;
import static com.matrictime.network.constant.DataConstants.SYSTEM_IM;
import static com.matrictime.network.constant.DataConstants.SYSTEM_UC;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project microservicecloud-jzsg
 * @date 2021/9/10 0010 16:21
 * @desc
 */
@Slf4j
@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

    @Autowired
    private LoginService loginService;

    @Value("${im.pushTokenUrl}")
    private String pushTokenUrl;


    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    /**
     *
     * @param message
     * @param pattern
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        if(ObjectUtils.isEmpty(message)){
            return;
        }
        try {
            //1.0 寻找key值
            if(message.toString().contains(SYSTEM_UC+USER_LOGIN_JWT_TOKEN)){
                log.info("过期的token的key:{}",message.toString());

                String[] strings = message.toString().split(KEY_SPLIT_UNDERLINE);
                String userId =strings[3];
                String destination = strings[4];
                log.info("过期token的用户id是:{},des:{}",userId,destination);

                LogoutReq logoutReq = new LogoutReq();
                logoutReq.setUserId(userId);
                logoutReq.setDestination(destination);
                loginService.syslogout(logoutReq);

                JSONObject param = new JSONObject();
                param.put("type",PUSH_TYPE_DISABLE_TOKEN_INVALID);
                param.put("userid",userId);
                param.put("scope",destination);
                PushTokenReq req = new PushTokenReq();
                req.setType(PUSH_TYPE_DISABLE_TOKEN);
                req.setUrl(pushTokenUrl);
                req.setSystem(SYSTEM_IM);
                req.setBody(param.toString());
                loginService.pushToken(req);

                log.info("修改过期token的用户id：{}",userId);
            }
        }catch (Exception e){
            log.error("监听过期token{}，发生异常：{}",message,e.getMessage());
        }

    }

}
