package com.matrictime.network.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录注册
 */
@RequestMapping(value = "/user")
@RestController
@Slf4j
public class LoginController {

    /**
     * 登录
     * @return
     */
    @RequestMapping(value = "/login")
    public String login(){
        return "login";
    }

    /**
     * 注册
     * @return
     */
    @RequestMapping(value = "/register")
    public String register(){
        return "register";
    }

    /**
     * 退出
     * @return
     */
    @RequestMapping(value = "/logout")
    public String logout(){
        return "logout";
    }
}
