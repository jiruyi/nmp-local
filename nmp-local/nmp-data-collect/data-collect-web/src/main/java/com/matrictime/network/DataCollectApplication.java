package com.matrictime.network;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@EnableEurekaClient //本服务启动后会自动注册进eureka服务中
//@EnableDiscoveryClient //服务发现
@MapperScan(basePackages = "com.matrictime.network.dao.mapper")
//@EnableRetry(proxyTargetClass = true)
@EnableScheduling
public class DataCollectApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(DataCollectApplication.class, args);
	}
}
