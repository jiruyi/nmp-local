package com.matrictime.network;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
@MapperScan(basePackages = "com.matrictime.network.dao.mapper")
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.matrictime.network")
public class NetworkProxyApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(NetworkProxyApplication.class, args);
    }
}
