package com.matrictime.network.interceptor;

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
                .excludePathPatterns("/**/user/login/**")
                .excludePathPatterns("/**/health/**")
                .excludePathPatterns("/**/log/device/save/**")
                .excludePathPatterns("/**/device/supplementDeviceInfo/**")
                .excludePathPatterns("/**/node/getAdjoinNode/**")
                .excludePathPatterns("/**/node/getNode/**")
                .excludePathPatterns("/**/route/query/**")
                .excludePathPatterns("/**/keyDetail/insertKey/**")
                .excludePathPatterns("/**/beans/**")
                .excludePathPatterns("/**/error/**")
                .excludePathPatterns("/**/swagger-resources/**")
                .excludePathPatterns("/**/v3/**")
                .excludePathPatterns("/**/swagger-ui/**")
                .excludePathPatterns("/**/sms/send")
                .excludePathPatterns("/**/device/selectDevice/**")
                .excludePathPatterns("/**/druid/**")
                .excludePathPatterns("/**/data/saveData/**")
                .excludePathPatterns("/**/bill/saveBill/**")
                .excludePathPatterns("/signal/addSignal")
                .excludePathPatterns("/monitor/checkHeart")
                .excludePathPatterns("/version/getVersionFile")
                .excludePathPatterns("/linkRelation/selectDevice")
                .excludePathPatterns("/baseStation/selectDevice")
                .excludePathPatterns("/baseStation/selectByOperatorId")
                .excludePathPatterns("/outlineSorter/auth")
                .excludePathPatterns("/config/insertOrUpdate")
                .excludePathPatterns("/proxy/init")
                .excludePathPatterns("/alarm/accept")
                .excludePathPatterns("/version/uploadVersionFile")
                .excludePathPatterns("/version/updateVersionFile")
                .excludePathPatterns("/version/deleteVersionFile")
                .excludePathPatterns("/version/selectVersionFile")
                .excludePathPatterns("/baseStation/savePcData")
                .excludePathPatterns("/terminalUser/updateTerminalUser")
                .excludePathPatterns("/systemHeartbeat/updateSystemHeartbeat")
                .excludePathPatterns("/terminalData/collectTerminalData")
                .excludePathPatterns("/systemDataCollect/insertSystemData")
                .excludePathPatterns("/monitor/physicalDeviceHeartbeat")
                .excludePathPatterns("/monitor/physicalDeviceResource")
                .excludePathPatterns("/monitor/systemResource");
    }
}
