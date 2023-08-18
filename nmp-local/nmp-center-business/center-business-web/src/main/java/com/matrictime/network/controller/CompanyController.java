package com.matrictime.network.controller;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.TerminalUserRequest;
import com.matrictime.network.response.CompanyInfoResponse;
import com.matrictime.network.service.CompanyService;
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
 * @date 2023/8/18.
 */

@RequestMapping(value = "/company")
@RestController
@Slf4j
public class CompanyController {

    @Resource
    private CompanyService companyService;

    @RequestMapping(value = "/receiveCompany")
    public Result<Integer> receiveCompany(@RequestBody CompanyInfoResponse companyInfoResponse){
       Result<Integer> result = new Result<>();
       try {
           if(ObjectUtils.isEmpty(companyInfoResponse)){
               return new Result<>(false,"上传参数为空");
           }
           if(CollectionUtils.isEmpty(companyInfoResponse.getList())){
               return new Result<>(false,"上传参数为空");
           }
           result = companyService.receiveCompany(companyInfoResponse);
       }catch (Exception e){
           log.info("receiveCompany:{}",e.getMessage());
           result.setSuccess(false);
           result.setErrorMsg(e.getMessage());
       }
        return result;
    }

}
