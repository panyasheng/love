package com.pan.love.framework.config.log;

import java.lang.annotation.*;
/**
 * 用户操作日志
 *
 * @author pan
 * @date 2019/10/29
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UserLog {

    /**
     * 操作描述
     * @return
     */
    String description();
}
