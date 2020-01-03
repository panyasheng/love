package com.pan.love.util;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 生成token
 * 使用jwt生成token,每次登陆过滤器需要校验token是否有效，校验方法checkToken
 * 也可以生成token自己写入redis,设置有效时间
 * @author pan
 * @date 2019/11/1
 */
public class GenerateToken {

    static final Logger logger= LoggerFactory.getLogger(GenerateToken.class);

    public static final String SECRET_KEY="pan";


    /**
     * 通过字符串生成token
     * @param userId
     * @param tokenExp token有效时间 默认时间1000*60*60 单位毫秒
     * @author pan
     * @date 2019/11/4
     */
    public static String getToken(String userId, long tokenExp) {
        if (tokenExp == 0) {
            tokenExp = 1000 * 60 * 60;
        }
        return Jwts.builder()
                .setSubject(userId)
                .claim("userId", userId)
                .setIssuedAt(new Date())
                /*过期时间*/
                .setExpiration(new Date(System.currentTimeMillis() + tokenExp))
                .signWith(SignatureAlgorithm.HS256, getBase64EncodedSecretKey(SECRET_KEY))
                .compact();
    }

    /**
     * 生成64位的字节码
     *
     * @author pan
     * @date 2019/11/4
     */
    private static byte[] getBase64EncodedSecretKey(String secretKey) {
        byte[] base64EncodedSecretKey = Base64.getDecoder().decode(secretKey);
        return base64EncodedSecretKey;
    }

    /**
     * 通过map生成token
     *
     * @param var1 保存的数据
     * @param tokenExp token有效时间 默认时间1000*60*60 单位毫秒
     * @author pan
     * @date 2019/11/4
     */
    public static String getTokenByMap(Map<String, Object> var1, long tokenExp) {
        if (tokenExp == 0) {
            tokenExp = 1000 * 60 * 60;
        }
        return Jwts.builder()
                .setClaims(var1)
                .setIssuedAt(new Date())
                /*过期时间*/
                .setExpiration(new Date(System.currentTimeMillis()+tokenExp))
                .signWith(SignatureAlgorithm.HS256, getBase64EncodedSecretKey(SECRET_KEY))
                .compact();
    }


    /**
     * 校验token
     *
     * @author pan
     * @date 2019/11/4
     */
    public static Map<String,Object> checkToken(String token){
        boolean result = false;
        Claims claims;
        String msg = "校验token成功";
        try {
            claims = Jwts.parser().setSigningKey(getBase64EncodedSecretKey(SECRET_KEY)).parseClaimsJws(token).getBody();
            if(claims !=null){
                result=true;
            }
        } catch (ExpiredJwtException e1) {
            msg = "token已经过期";

        } catch (Exception e) {
            msg = "无效token";
        }
        logger.info(msg);
        Map<String,Object> mapResult=new HashMap<>(16);
        mapResult.put("result",result);
        mapResult.put("msg",msg);
        return mapResult;
    }

    public static void main(String[] args) {


        String str=getToken("hahah", 0);
        System.out.println(str);
        Map<String, Object> map = new HashMap<>(16);
        map.put("userId", 1);

        String string=getTokenByMap(map, 1000*60);
        System.out.println(string);

        System.out.println(checkToken("eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1NzI4NjE0NTUsInVzZXJJZCI6MSwiaWF0IjoxNTcyODYxMzk1fQ.uCJ-3TaZEf-Cy_GziVvfxWwZ7wMRSStcno4V7sKXwOQ"));

        System.out.println(new Date(System.currentTimeMillis()+5*60*1000));

    }
}
