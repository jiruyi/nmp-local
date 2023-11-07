package com.matrictime.network.controller;

import com.matrictime.network.model.Result;
import com.matrictime.network.req.CaManageRequest;
import com.matrictime.network.service.CaManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author by wangqiang
 * @date 2023/11/2.
 */
@RequestMapping(value = "/caManage")
@RestController
@Slf4j
public class CaManageController {

    @Resource
    private CaManageService caManageService;

    @RequestMapping(value = "/insertCaManage",method = RequestMethod.POST)
    public Result insertCaManage(@RequestBody CaManageRequest caManageRequest){
        try {
            Result result = caManageService.insertCaManage(caManageRequest);
            return result;
        }catch (Exception e){
            log.error("insertCaManage exception:{}",e.getMessage());
            return new Result(false, e.getMessage());
        }
    }

    @RequestMapping(value = "/selectCaManage",method = RequestMethod.POST)
    public Result selectIpManage(@RequestBody CaManageRequest caManageRequest){
        try {
            Result result = caManageService.selectCaManage(caManageRequest);
            return result;
        }catch (Exception e){
            log.error("selectCaManage exception:{}",e.getMessage());
            return new Result(false, e.getMessage());
        }
    }

    @RequestMapping(value = "/deleteCaManage",method = RequestMethod.POST)
    public Result deleteIpManage(@RequestBody CaManageRequest caManageRequest){
        try {
            if(StringUtils.isEmpty(caManageRequest.getNetworkId())){
                return new Result<>(false,"必传参没传");
            }
            Result result = caManageService.deleteCaManage(caManageRequest);
            return result;
        }catch (Exception e){
            log.error("deleteCaManage exception:{}",e.getMessage());
            return new Result(false, e.getMessage());
        }
    }

    @RequestMapping(value = "/updateCaManage",method = RequestMethod.POST)
    public Result updateIpManage(@RequestBody CaManageRequest caManageRequest){
        try {
            if(StringUtils.isEmpty(caManageRequest.getNetworkId()) ||
                    StringUtils.isEmpty(caManageRequest.getId())){
                return new Result<>(false,"必传参没传");
            }
            Result result = caManageService.updateCaManage(caManageRequest);
            return result;
        }catch (Exception e){
            log.error("updateCaManage exception:{}",e.getMessage());
            return new Result(false, e.getMessage());
        }
    }
}
