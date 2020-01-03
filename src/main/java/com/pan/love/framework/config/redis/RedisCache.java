package com.pan.love.framework.config.redis;

import java.lang.annotation.*;

/**
 * redis 缓存注解
 *
 * @author pan
 * @date 2019/10/25
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
@Documented
public @interface RedisCache {

    String keyName() default "";
    int cacheTime() default -1;
}
