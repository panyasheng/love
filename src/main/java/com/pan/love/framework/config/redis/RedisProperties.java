package com.pan.love.framework.config.redis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * redis配置文件
 *
 * @author pan
 * @date 2019/10/25
 */
@Configuration
@ConfigurationProperties(prefix = "spring.redis")
@Data
public class RedisProperties {

    private String host;
    private String port;
}
