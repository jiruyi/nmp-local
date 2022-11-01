package controller;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan(basePackages = "com.matrictime.network.dao.mapper")
@EnableTransactionManagement
public class PortalApplication {
    public static void main(String[] args)
    {
        SpringApplication.run(PortalApplication.class, args);
    }
}
