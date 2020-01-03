package com.pan.love.framework.config.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**
 * 拦截器
 *
 * @author pan
 * @date 2019/10/31
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;

    /**
     * 不需要登录拦截的url
     */
    final String[] notLoginInterceptPaths = {
            "/static/**",
            "/user/login",
            "/user/getCode",
            "/login",
            //放行swagger
            "/swagger-ui.html/**",
            "/swagger-resources/**"
    };


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //可以添加多个拦截器
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(notLoginInterceptPaths);
    }

    /**
     * 不需要经过controller就跳转到的页面
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
//        registry.addRedirectViewController();
    }

    /**
     * addResourceLocations指的是文件放置的目录，addResourceHandler指的是对外暴露的访问路径
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/static/**")
                .addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"/static/");

        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}
