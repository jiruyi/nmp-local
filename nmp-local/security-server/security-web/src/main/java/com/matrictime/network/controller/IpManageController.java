package com.matrictime.network.controller;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.ConfigurationValueRequest;
import com.matrictime.network.request.IpManageRequest;
import com.matrictime.network.service.IpManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author by wangqiang
 * @date 2023/10/27.
 */
@RequestMapping(value = "/ipManage")
@RestController
@Slf4j
public class IpManageController {

    @Resource
    private IpManageService ipManageService;

    @RequestMapping(value = "/insertIpManage",method = RequestMethod.POST)
    public Result<Integer> insertIpManage(@RequestBody IpManageRequest ipManageRequest){
        try {
            Result result = ipManageService.insertIpManage(ipManageRequest);
            return result;
        }catch (Exception e){
            log.error("insertIpManage exception:{}",e.getMessage());
            return new Result(false, e.getMessage());
        }
    }

    @RequestMapping(value = "/selectIpManage",method = RequestMethod.POST)
    public Result<Integer> selectIpManage(@RequestBody IpManageRequest ipManageRequest){
        try {
            if(StringUtils.isEmpty(ipManageRequest.getManageType())){
               return new Result<>(false,"必传参没传");
            }
            Result result = ipManageService.selectIpManage(ipManageRequest);
            return result;
        }catch (Exception e){
            log.error("selectIpManage exception:{}",e.getMessage());
            return new Result(false, e.getMessage());
        }
    }

    @RequestMapping(value = "/deleteIpManage",method = RequestMethod.POST)
    public Result<Integer> deleteIpManage(@RequestBody IpManageRequest ipManageRequest){
        try {
            if(StringUtils.isEmpty(ipManageRequest.getNetworkId())){
                return new Result<>(false,"必传参没传");
            }
            Result result = ipManageService.deleteIpManage(ipManageRequest);
            return result;
        }catch (Exception e){
            log.error("deleteIpManage exception:{}",e.getMessage());
            return new Result(false, e.getMessage());
        }
    }

    @RequestMapping(value = "/updateIpManage",method = RequestMethod.POST)
    public Result<Integer> updateIpManage(@RequestBody IpManageRequest ipManageRequest){
        try {
            Result result = ipManageService.updateIpManage(ipManageRequest);
            return result;
        }catch (Exception e){
            log.error("updateIpManage exception:{}",e.getMessage());
            return new Result(false, e.getMessage());
        }
    }
}
