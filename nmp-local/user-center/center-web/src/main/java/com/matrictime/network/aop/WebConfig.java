package com.matrictime.network.aop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project microservicecloud-jzsg
 * @date 2021/9/7 0007 14:48
 * @desc
 */
@Slf4j
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private SessionHandlerInterceptor sessionHandlerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionHandlerInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/**/health/**")
                .excludePathPatterns("/**/node/getAdjoinNode/**")
                .excludePathPatterns("/**/node/getNode/**")
                .excludePathPatterns("/**/route/query/**")
                .excludePathPatterns("/**/beans/**")
                .excludePathPatterns("/**/error/**")
                .excludePathPatterns("/**/swagger-resources/**")
                .excludePathPatterns("/**/v3/**")
                .excludePathPatterns("/**/swagger-ui/**")
                .excludePathPatterns("/**/druid/**")
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/user/register")
                .excludePathPatterns("/user/syslogout")
                .excludePathPatterns("/user/zr")
                .excludePathPatterns("/ext/**")
                .excludePathPatterns("/changePasswd")
                .excludePathPatterns("/verify");
    }
}
