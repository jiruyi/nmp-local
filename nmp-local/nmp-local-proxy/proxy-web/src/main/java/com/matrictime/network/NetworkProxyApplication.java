package com.matrictime.network;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableDiscoveryClient //服务发现
@EnableTransactionManagement
@MapperScan(basePackages = "com.matrictime.network.dao.mapper")
public class NetworkProxyApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(NetworkProxyApplication.class, args);
    }
}
