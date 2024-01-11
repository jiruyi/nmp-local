package com.matrictime.network.config;

import com.matrictime.network.base.enums.ProxyConfigEnum;
import com.matrictime.network.dao.mapper.NmplLocalBaseStationInfoMapper;
import com.matrictime.network.dao.mapper.NmplProxyConfigMapper;
import com.matrictime.network.dao.model.NmplLocalBaseStationInfo;
import com.matrictime.network.dao.model.NmplProxyConfig;
import com.matrictime.network.dao.model.NmplProxyConfigExample;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * @author jiruyi@goldengintech.com.cn
 * @project mini_know
 * @date 2019-2019/10/10-19:29
 * @desc
 */
@Configuration
public class MyRedisConfig {

    @Resource
    NmplProxyConfigMapper nmplProxyConfigMapper;

    @Bean(name = "redisTemplate")
    public RedisTemplate<String,Object> getRedisTemplate(RedisConnectionFactory factory){
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<String,Object>();

        redisTemplate.setConnectionFactory(factory);
        GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        return  redisTemplate;
    }

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
//        container.addMessageListener(messageListener(),new ChannelTopic(DataConstants.DEVICE_LOG_REDIS_CHANNEL));
        return container;
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        NmplProxyConfigExample nmplProxyConfigExample = new NmplProxyConfigExample();
        nmplProxyConfigExample.createCriteria().andConfigCodeEqualTo(ProxyConfigEnum.REDIS_IP.getCode());
        List<NmplProxyConfig> ipConfig = nmplProxyConfigMapper.selectByExample(nmplProxyConfigExample);


        nmplProxyConfigExample.clear();
        nmplProxyConfigExample.createCriteria().andConfigCodeEqualTo(ProxyConfigEnum.REDIS_PASSWORD.getCode());
        List<NmplProxyConfig> passwordConfig = nmplProxyConfigMapper.selectByExample(nmplProxyConfigExample);


        if(CollectionUtils.isEmpty(ipConfig) || CollectionUtils.isEmpty(passwordConfig)){
            throw new RuntimeException("redis has no configuration information ");
        }

        System.out.println("redis ip is:" +ipConfig.get(0).getConfigValue());
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(ipConfig.get(0).getConfigValue(), 6379);
        config.setPassword(RedisPassword.of(passwordConfig.get(0).getConfigValue()));
        return new LettuceConnectionFactory(config);
    }

}
