package com.matrictime.network.controller;

import com.matrictime.network.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;


@Component
@Slf4j
public class MyStartupRunner implements CommandLineRunner {

    @Autowired
    private TaskService taskService;


    @Override
    public void run(String... args){
        taskService.initData();
    }

    @PreDestroy
    public void destory() throws Exception{
        log.info("在程序关闭后执行");
    }
}
