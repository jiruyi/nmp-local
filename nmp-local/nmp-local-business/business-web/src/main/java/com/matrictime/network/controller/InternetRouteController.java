package com.matrictime.network.controller;

import com.matrictime.network.base.exception.ErrorMessageContants;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.InternetRouteRequest;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.InternetRouteService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author by wangqiang
 * @date 2022/9/29.
 */
@Slf4j
@RestController
@RequestMapping(value = "/internetRoute",method = RequestMethod.POST)
public class InternetRouteController {

    @Resource
    private InternetRouteService internetRouteService;

    @RequiresPermissions("sys:internetRoute:insert")
    @RequestMapping(value = "/insertInternetRoute",method = RequestMethod.POST)
    public Result<Integer> insertInternetRoute(@RequestBody InternetRouteRequest internetRouteRequest){
        try {
            if(StringUtils.isEmpty(internetRouteRequest.getNetworkId())){
                return new Result<>(false, ErrorMessageContants.NETWORK_ID_IS_NULL_MSG);
            }
            if(StringUtils.isEmpty(internetRouteRequest.getBoundaryStationIp()) && StringUtils.isEmpty(internetRouteRequest.getIpV6())){
                return new Result<>(false,ErrorMessageContants.DEVICE_IP_IS_NULL_MSG);
            }
            return internetRouteService.insert(internetRouteRequest);
        }catch (Exception e){
            log.info("insertInternetRoute:{}",e.getMessage());
            return new Result<>(false,"");
        }
    }

    @RequiresPermissions("sys:internetRoute:delete")
    @RequestMapping(value = "/deleteInternetRoute",method = RequestMethod.POST)
    public Result<Integer> deleteInternetRoute(@RequestBody InternetRouteRequest internetRouteRequest){
        try {
            if(ObjectUtils.isEmpty(internetRouteRequest.getId())){
                return new Result<>(false, ErrorMessageContants.ID_IS_NULL_MSG);
            }
            return internetRouteService.delete(internetRouteRequest);
        }catch (Exception e){
            log.info("deleteInternetRoute:{}",e.getMessage());
            return new Result<>(false,"");
        }
    }

    @RequiresPermissions("sys:internetRoute:update")
    @RequestMapping(value = "/updateInternetRoute",method = RequestMethod.POST)
    public Result<Integer> updateInternetRoute(@RequestBody InternetRouteRequest internetRouteRequest){
        try {
            if(StringUtils.isEmpty(internetRouteRequest.getNetworkId())){
                return new Result<>(false, ErrorMessageContants.NETWORK_ID_IS_NULL_MSG);
            }
            if(StringUtils.isEmpty(internetRouteRequest.getBoundaryStationIp()) && StringUtils.isEmpty(internetRouteRequest.getIpV6())){
                return new Result<>(false,ErrorMessageContants.DEVICE_IP_IS_NULL_MSG);
            }
            return internetRouteService.update(internetRouteRequest);
        }catch (Exception e){
            log.info("updateInternetRoute:{}",e.getMessage());
            return new Result<>(false,"");
        }
    }

    @RequiresPermissions("sys:internetRoute:select")
    @RequestMapping(value = "/selectInternetRoute",method = RequestMethod.POST)
    public Result<PageInfo> selectInternetRoute(@RequestBody InternetRouteRequest internetRouteRequest){
        try {
            return internetRouteService.select(internetRouteRequest);
        }catch (Exception e){
            log.info("selectInternetRoute:{}",e.getMessage());
            return new Result<>(false,"");
        }
    }

}


