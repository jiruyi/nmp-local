package com.matrictime.network.controller;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.TerminalUserRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author by wangqiang
 * @date 2023/8/14.
 */



@RequestMapping(value = "/companyTerminalUser")
@RestController
@Slf4j
public class CompanyTerminalUserController {

    @RequestMapping(value = "/selectCompanyTerminalUser")
    public Result selectCompanyTerminalUser(@RequestBody TerminalUserRequest userRequest){
        if(!StringUtils.isEmpty(userRequest.getTerminalStatus())){
            return new Result<>(false,"缺少必传参数");
        }
        if(!StringUtils.isEmpty(userRequest.getCompanyNetworkId())){
            return new Result<>(false,"缺少必传参数");
        }


        return null;
    }
}
