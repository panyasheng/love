package com.pan.love.framework.config.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 用户操作日志类
 *
 * @author pan
 * @date 2019/10/29
 */
@Aspect
@Component
public class UserLogAspect {

    @Around("@annotation(com.pan.love.framework.config.log.UserLog)")
    public Object operationLogAround(ProceedingJoinPoint point){
        //TODO 用户操作日志逻辑待处理
        return "";
    }
}
