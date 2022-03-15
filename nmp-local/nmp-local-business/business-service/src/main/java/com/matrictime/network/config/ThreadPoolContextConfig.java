package com.matrictime.network.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class ThreadPoolContextConfig {

    private final Integer CORE_POOL_SIZE = 5;

    private final Integer MAXI_MUM_POOL_SIZE = 10;

    private final Long KEEP_ALIVE_TIME = 1L;

    @Bean
    public ThreadPoolExecutor getThreadPoolExecutor(){
        ExecutorService threadPool = new ThreadPoolExecutor(CORE_POOL_SIZE,MAXI_MUM_POOL_SIZE,
                KEEP_ALIVE_TIME, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>());
        return (ThreadPoolExecutor) threadPool;
    }




}