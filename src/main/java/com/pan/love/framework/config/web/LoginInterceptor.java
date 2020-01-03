package com.pan.love.framework.config.web;

import com.pan.love.framework.exception.LoveException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Map;

import static com.pan.love.util.GenerateToken.checkToken;

/**
 * 登录拦截器
 *
 * @author pan
 * @date 2019/10/31
 */
@Configuration
public class LoginInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger= LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Object token=request.getHeader("token");
        Map<String,Object> mapResult=checkToken(token+"");
        boolean b= (boolean) mapResult.get("result");
        if(!b){
//            logger.info("未登录，跳转到登录页面");
//            response.setHeader("Content-Type","text/html;charset=UTF-8");
//            response.sendRedirect(request.getContextPath()+"/login");
            logger.info(mapResult.get("msg")+"");
            throw new LoveException(mapResult.get("msg"));
        }
        return true;
    }
}
