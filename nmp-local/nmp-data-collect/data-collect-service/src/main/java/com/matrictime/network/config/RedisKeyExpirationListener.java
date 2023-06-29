package com.matrictime.network.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

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

    @LoadBalanced
    @Bean
    public RestTemplate rest() {
        return new RestTemplate();
    }


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
//            if(message.toString().contains(DataConstants.HEART_CHECK_DEVICE_ID)){
//                log.info("过期的设备心跳key:{}",message.toString());
//                //2.0设备状态修改
//                String deviceId =(message.toString().split(":"))[NumberUtils.INTEGER_ONE];
//
//            }
        }catch (Exception e){
            log.error("监听过期心跳{}，发生异常：{}",message,e.getMessage());
        }

    }

}
