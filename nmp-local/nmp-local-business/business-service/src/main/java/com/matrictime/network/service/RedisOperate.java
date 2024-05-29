package com.matrictime.network.service;

import com.alibaba.fastjson.JSON;
import com.matrictime.network.model.ChangeType;
import com.matrictime.network.model.RedisMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2024/4/24 0024 16:03
 * @desc
 */
@Service
@Slf4j
public class RedisOperate {

    @Autowired
    private Executor taskExecutor;

    @Autowired
    private RedisTemplate redisTemplate;



    /**
      * @title addRedisData
      * @param [key, id, message]
      * @return void
      * @description
      * @author jiruyi
      * @create 2024/4/24 0024 16:35
      */
    public void addRedisData(String key,String id, Object message){
        RedisMsg redisMsg = RedisMsg.builder().redis_key(key).id(id)
                .change_type(ChangeType.ADD.getCode()).build();
        publishRedisMsg(redisMsg);
    }
    public void deleteRedisData(String key,String id, Object message){
        RedisMsg redisMsg = RedisMsg.builder().redis_key(key).id(id)
                .change_type(ChangeType.DELETE.getCode()).build();
        publishRedisMsg(redisMsg);
    }
    public void updateRedisData(String key,String id, Object message){
        RedisMsg redisMsg = RedisMsg.builder().redis_key(key).id(id)
                .change_type(ChangeType.UPDATE.getCode()).build();
        publishRedisMsg(redisMsg);
    }

    /**
      * @title publishRedisMsg
      * @param [redisMsg]
      * @return void
      * @description  redis 发送消息
      * @author jiruyi
      * @create 2024/4/24 0024 17:05
      */
    public void publishRedisMsg(RedisMsg redisMsg){
        log.info("RedisMsg  is {}   ",redisMsg.toString());
        if(Objects.isNull(redisMsg)){
            return;
        }
        //业务key
        String redis_key = redisMsg.getRedis_key();
        if(StringUtils.isEmpty(redis_key) || ObjectUtils.isEmpty(redisMsg.getId())
                || StringUtils.isEmpty(redisMsg.getChange_type())){
            log.info("redis_key is empty or id is empty or change_type is empty");
            return;
        }
        List<String> lists =  Arrays.asList(redis_key.split(":"));
        if(CollectionUtils.isEmpty(lists)|| lists.size() < 2){
            return;
        }
        redisTemplate.convertAndSend(lists.get(0),JSON.toJSONString(redisMsg));
    }
}
