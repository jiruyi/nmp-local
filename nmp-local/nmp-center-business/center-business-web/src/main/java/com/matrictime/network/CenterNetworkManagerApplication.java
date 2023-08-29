package com.matrictime.network;

import com.matrictime.network.base.util.SpringContextUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
//@EnableEurekaClient //本服务启动后会自动注册进eureka服务中
//@EnableDiscoveryClient //服务发现
@MapperScan(basePackages = "com.matrictime.network.dao.mapper")
//@EnableRetry(proxyTargetClass = true)
public class CenterNetworkManagerApplication
{
	public static void main(String[] args)
	{

		ConfigurableApplicationContext applicationContext
				= SpringApplication.run(CenterNetworkManagerApplication.class, args);
		SpringContextUtils.setApplicationContext(applicationContext);
	}
}
