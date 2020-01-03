package com.pan.love.framework.config.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * redis 缓存切面类
 *
 * @author pan
 * @date 2019/10/25
 */
@Aspect
@Component
public class RedisCacheAspect {
    private Logger logger= LoggerFactory.getLogger(RedisCacheAspect.class);

    @Autowired
    private JedisClientPool jedisClientPool;

    @Around("@annotation(com.pan.love.framework.config.redis.RedisCache)")
    public Object redisCache(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //得到参数
        String redisResult="";
        Object[] args=proceedingJoinPoint.getArgs();

        //得到被代理的方法
        Signature signature=proceedingJoinPoint.getSignature();
//        if(signature instanceof MethodSignature){
//            throw new LoveException("redis 参数出错");
//        }
        MethodSignature methodSignature= (MethodSignature) signature;
        //得到被代理的方法上的注解
        Method method=proceedingJoinPoint.getTarget().getClass().getMethod(methodSignature.getName(),methodSignature.getParameterTypes());

        Object result=null;

        //判断方法上是否有加入缓存的注解
        if(method.isAnnotationPresent(RedisCache.class)){
            String keyName=method.getAnnotation(RedisCache.class).keyName();
            int cacheTime=method.getAnnotation(RedisCache.class).cacheTime();

            String className=proceedingJoinPoint.getTarget().getClass().getName();
            String methodName=proceedingJoinPoint.getSignature().getName();

            //根据类型，方法名和参数生成key
            String key=getKey(className,methodName,args);
            if(keyName !=null && !"".equals(keyName)){
                key=keyName+":"+key;
            }
//            logger.info("生成的key[{}]",key);
            if(!jedisClientPool.exists(key)){
                logger.info("缓存未调用，调用原方法,放入缓存");
                result=proceedingJoinPoint.proceed(args);
                redisResult= JSONObject.toJSONString(result, SerializerFeature.WriteMapNullValue);

                if(cacheTime==-1){
                    jedisClientPool.set(key,redisResult);
                }else {
                    jedisClientPool.set(key,redisResult,cacheTime);
                }
            }else {
                logger.info("缓存存在,调用缓存");
                redisResult=jedisClientPool.get(key);
                //得到被代理方法的返回值类型
                Class returnType=method.getReturnType();
                result=JSON.parseObject(redisResult,returnType);
            }
        }
        //判断方式是否有删除缓存的注解
        else if(method.isAnnotationPresent(RedisCacheDel.class)){
            result =proceedingJoinPoint.proceed(args);
            RedisCacheDel redisCacheDel=method.getAnnotation(RedisCacheDel.class);
            //是否删除所有
            Boolean all=redisCacheDel.all();
            String s=redisCacheDel.keyName();
            if(all){
                Set<String> keys=jedisClientPool.keys(s+"*");
                for (String s1:keys){
                    jedisClientPool.del(s1);
                }
            }else{
                jedisClientPool.del(s);
            }
        }else {
            result=proceedingJoinPoint.proceed(args);
        }
        return result;
    }

    private String getKey(String className, String methodName, Object[] args) {
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append(className)
                .append(".")
                .append(methodName);
        for (Object object:args) {
            //参数
            //logger.info("obj:"+object);
            if(object !=null){
                stringBuilder.append("_")
                        .append(object+"");
            }
        }
        return stringBuilder.toString();
    }
}
