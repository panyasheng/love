package com.pan.love.framework.config.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.JedisPool;

/**
 * redis 配置
 *
 * @author pan
 * @date 2019/10/25
 */
@Configuration
public class RedisConfig{

    @Autowired
    private RedisProperties redisProperties;

    @Bean
    public JedisPool jedisPool(){
        String host=redisProperties.getHost();
        Integer port=Integer.parseInt(redisProperties.getPort());
        return new JedisPool(host,port);
    }

    public static String getPackages(){
        return "com.pan.love";
    }
}