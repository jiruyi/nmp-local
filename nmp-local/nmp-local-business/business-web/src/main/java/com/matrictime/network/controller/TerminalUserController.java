package com.matrictime.network.controller;

import com.matrictime.network.annotation.SystemLog;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.TerminalUserResquest;
import com.matrictime.network.response.TerminalUserCountResponse;
import com.matrictime.network.response.TerminalUserResponse;
import com.matrictime.network.service.TerminalUserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author by wangqiang
 * @date 2023/4/19.
 */
@RequestMapping(value = "/terminalUser")
@RestController
@Slf4j
@Api(value = "终端用户管理",tags = "终端用户管理")
public class TerminalUserController {

    @Resource
    private TerminalUserService terminalUserService;

    //@SystemLog(opermodul = "终端用户管理",operDesc = "更新用户状态",operType = "更新")
    @RequestMapping(value = "/updateTerminalUser",method = RequestMethod.POST)
    public Result<Integer> updateTerminalUser(@RequestBody TerminalUserResponse terminalUserResponse){
        Result<Integer> result = new Result<>();
        try {
            result = terminalUserService.updateTerminalUser(terminalUserResponse);
        }catch (Exception e){
            log.info("updateTerminalUser:{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("");
        }
        return result;
    }

    @SystemLog(opermodul = "终端用户管理",operDesc = "查询各个用户总数",operType = "查询各个用户总数")
    @RequestMapping(value = "/countTerminalUser",method = RequestMethod.POST)
    public Result<TerminalUserCountResponse> countTerminalUser(@RequestBody TerminalUserResquest terminalUserResquest){
        Result<TerminalUserCountResponse> result = new Result<>();
        try {
            result = terminalUserService.countTerminalUser(terminalUserResquest);
        }catch (Exception e){
            log.info("countTerminalUser:{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("");
        }
        return result;
    }




















}
