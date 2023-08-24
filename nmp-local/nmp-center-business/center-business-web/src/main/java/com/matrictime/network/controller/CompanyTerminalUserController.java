package com.matrictime.network.controller;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.TerminalUserRequest;
import com.matrictime.network.response.TerminalUserResponse;
import com.matrictime.network.service.TerminalUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author by wangqiang
 * @date 2023/8/14.
 */



@RequestMapping(value = "/companyTerminalUser")
@RestController
@Slf4j
public class CompanyTerminalUserController {

    @Resource
    private TerminalUserService terminalUserService;


    @RequestMapping(value = "/receiveTerminalUser")
    public Result receiveTerminalUser(@RequestBody TerminalUserResponse terminalUserResponse){
        Result result = new Result<>();
        try {
            if(ObjectUtils.isEmpty(terminalUserResponse)){
                return new Result<>(false,"上传数据为空");
            }
            if(CollectionUtils.isEmpty(terminalUserResponse.getList())){
                return new Result<>(false,"上传数据为空");
            }
            result = terminalUserService.receiveTerminalUser(terminalUserResponse);
        }catch (Exception e){
            log.info("receiveTerminalUser:{}",e.getMessage());
            result.setErrorMsg(e.getMessage());
            result.setSuccess(false);
        }

        return result;
    }
}
