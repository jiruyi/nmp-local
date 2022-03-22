package com.matrictime.network;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.matrictime.network.dao.mapper")
public class UserCenterApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(UserCenterApplication.class, args);
	}
}
