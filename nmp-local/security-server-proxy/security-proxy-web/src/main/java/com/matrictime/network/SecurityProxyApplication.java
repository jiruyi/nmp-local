package com.matrictime.network;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan(basePackages = "com.matrictime.network.dao.mapper")
@EnableTransactionManagement
@EnableScheduling
@EnableAsync
public class SecurityProxyApplication {
    public static void main(String[] args)
    {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(SecurityProxyApplication.class, args);

    }
}
