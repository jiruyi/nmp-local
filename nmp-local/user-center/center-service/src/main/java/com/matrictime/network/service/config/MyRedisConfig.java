package com.matrictime.network.service.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import static com.matrictime.network.constant.DataConstants.KEY_SPLIT;

/**
 * @author jiruyi@goldengintech.com.cn
 * @project mini_know
 * @date 2019-2019/10/10-19:29
 * @desc
 */
@Configuration
public class MyRedisConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private String port;

    @Value("${spring.redis.password}")
    private String password;

    @Bean(name = "redisTemplate")
    public RedisTemplate<String,Object> getRedisTemplate(RedisConnectionFactory factory){
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<String,Object>();

        redisTemplate.setConnectionFactory(factory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
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
    public RedissonClient redisson(){
        Config config = new Config();
        String address = new StringBuffer("redis://").append(host).append(KEY_SPLIT).append(port).toString();
        config.useSingleServer().setAddress(address);
        if (null != password && !"".equals(password.trim())){
            config.useSingleServer().setPassword(password);
        }
        return Redisson.create(config);
    }

}
