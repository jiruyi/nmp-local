package com.matrictime.network;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan(basePackages = "com.matrictime.network.dao.mapper")
@EnableTransactionManagement
public class UserCenterApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(UserCenterApplication.class, args);
	}
}
