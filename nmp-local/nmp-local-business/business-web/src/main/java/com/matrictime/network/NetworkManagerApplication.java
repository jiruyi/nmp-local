package com.matrictime.network;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableEurekaClient //本服务启动后会自动注册进eureka服务中
@EnableDiscoveryClient //服务发现
@MapperScan(basePackages = "com.matrictime.network.dao.mapper")
@EnableRetry(proxyTargetClass = true)
public class NetworkManagerApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(NetworkManagerApplication.class, args);
	}
}
