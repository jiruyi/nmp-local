package com.matrictime.network.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Slf4j
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private SessionHandlerInterceptor sessionHandlerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionHandlerInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/**/user/login/**")
                .excludePathPatterns("/static/**")
                .excludePathPatterns("/**/static/**")
                .excludePathPatterns("/**/data/insert/**")
                .excludePathPatterns("/server/heartReport")
                .excludePathPatterns("/alarm/accept")
                .excludePathPatterns("/alarm/data/list")
                .excludePathPatterns("/**/swagger-ui/**")
                .excludePathPatterns("/**/swagger-resources/**")
                .excludePathPatterns("/**/v3/**")
                .excludePathPatterns("/security-server/error    ")
                .excludePathPatterns("/common/init");
    }
}
