package com.matrictime.network.controller;

import com.matrictime.network.model.Result;
import com.matrictime.network.req.CaManageRequest;
import com.matrictime.network.req.DnsManageRequest;
import com.matrictime.network.service.DnsManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author by wangqiang
 * @date 2023/11/3.
 */
@RequestMapping(value = "/dnsManage")
@RestController
@Slf4j
public class DnsManageController {

    @Resource
    private DnsManageService dnsManageService;

    /**
     * dns管理插入
     * @param dnsManageRequest
     * @return
     */
    @RequestMapping(value = "/insertDnsManage",method = RequestMethod.POST)
    public Result insertDnsManage(@RequestBody DnsManageRequest dnsManageRequest){
        try {
            if(StringUtils.isEmpty(dnsManageRequest.getNetworkId())){
                return new Result<>(false,"必传参没传");
            }
            Result result = dnsManageService.insertDnsManage(dnsManageRequest);
            return result;
        }catch (Exception e){
            log.error("insertDnsManage exception:{}",e.getMessage());
            return new Result(false, e.getMessage());
        }
    }

    /**
     * dns管理查询
     * @param dnsManageRequest
     * @return
     */
    @RequestMapping(value = "/selectDnsManage",method = RequestMethod.POST)
    public Result selectDnsManage(@RequestBody DnsManageRequest dnsManageRequest){
        try {
            Result result = dnsManageService.selectDnsManage(dnsManageRequest);
            return result;
        }catch (Exception e){
            log.error("selectDnsManage exception:{}",e.getMessage());
            return new Result(false, e.getMessage());
        }
    }

    /**
     * dns管理删除
     * @param dnsManageRequest
     * @return
     */
    @RequestMapping(value = "/deleteDnsManage",method = RequestMethod.POST)
    public Result deleteDnsManage(@RequestBody DnsManageRequest dnsManageRequest){
        try {
            if(StringUtils.isEmpty(dnsManageRequest.getNetworkId())){
                return new Result<>(false,"必传参没传");
            }
            Result result = dnsManageService.deleteDnsManage(dnsManageRequest);
            return result;
        }catch (Exception e){
            log.error("deleteDnsManage exception:{}",e.getMessage());
            return new Result(false, e.getMessage());
        }
    }

    /**
     * dns管理更新
     * @param dnsManageRequest
     * @return
     */
    @RequestMapping(value = "/updateDnsManage",method = RequestMethod.POST)
    public Result updateDnsManage(@RequestBody DnsManageRequest dnsManageRequest){
        try {
            if(StringUtils.isEmpty(dnsManageRequest.getNetworkId()) ||
                    StringUtils.isEmpty(dnsManageRequest.getId())){
                return new Result<>(false,"必传参没传");
            }
            Result result = dnsManageService.updateDnsManage(dnsManageRequest);
            return result;
        }catch (Exception e){
            log.error("updateDnsManage exception:{}",e.getMessage());
            return new Result(false, e.getMessage());
        }
    }
}
