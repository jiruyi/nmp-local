package com.matrictime.network.controller;

import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.DnsManageVo;
import com.matrictime.network.service.DnsManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author by wangqiang
 * @date 2023/11/6.
 */
@RestController
@RequestMapping(value = "/dnsManage",method = RequestMethod.POST)
@Slf4j
public class DnsManageController {

    @Resource
    private DnsManageService dnsManageService;

    @RequestMapping(value = "/insertDnsManage",method = RequestMethod.POST)
    public Result insertCaManage(@RequestBody DnsManageVo dnsManageVo){
        try {
            Result result = dnsManageService.insertDnsManage(dnsManageVo);
            return result;
        }catch (Exception e){
            log.error("insertDnsManage exception:{}",e.getMessage());
            return new Result(false, e.getMessage());
        }
    }

    @RequestMapping(value = "/deleteDnsManage",method = RequestMethod.POST)
    public Result deleteDnsManage(@RequestBody DnsManageVo dnsManageVo){
        try {
            Result result = dnsManageService.deleteDnsManage(dnsManageVo);
            return result;
        }catch (Exception e){
            log.error("deleteDnsManage exception:{}",e.getMessage());
            return new Result(false, e.getMessage());
        }
    }
}
