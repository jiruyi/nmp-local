package com.matrictime.network.controller;

import com.matrictime.network.base.exception.ErrorMessageContants;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.BaseStationInfoRequest;
import com.matrictime.network.request.BusinessRouteRequest;
import com.matrictime.network.response.BaseStationInfoResponse;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.BusinessRouteService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author by wangqiang
 * @date 2022/9/28.
 */
@Slf4j
@RestController
@RequestMapping(value = "/businessRoute",method = RequestMethod.POST)
public class BusinessRouteController {

    @Resource
    private BusinessRouteService businessRouteService;

    /**
     * 插入业务路由
     * @param businessRouteRequest
     * @return
     */

    //@RequiresPermissions("sys:businessRoute:insert")
    @RequestMapping(value = "/insertBusinessRoute",method = RequestMethod.POST)
    public Result<Integer> insertBusinessRoute(@RequestBody BusinessRouteRequest businessRouteRequest){
        try {
            if(StringUtils.isEmpty(businessRouteRequest.getNetworkId())){
                return new Result<>(false, ErrorMessageContants.NETWORK_ID_IS_NULL_MSG);
            }
            return businessRouteService.insert(businessRouteRequest);
        }catch (Exception e){
            log.info("insertBusinessRoute:{}",e.getMessage());
            return new Result<>(false,"系统异常，请稍后重试！");
        }
    }

    /**
     * 删除业务路由
     * @param businessRouteRequest
     * @return
     */
    //@RequiresPermissions("sys:businessRoute:delete")
    @RequestMapping(value = "/deleteBusinessRoute",method = RequestMethod.POST)
    public Result<Integer> deleteBusinessRoute(@RequestBody BusinessRouteRequest businessRouteRequest){
        try {
            if(ObjectUtils.isEmpty(businessRouteRequest.getId())){
                return new Result<>(false, ErrorMessageContants.ID_IS_NULL_MSG);
            }
            return businessRouteService.delete(businessRouteRequest);
        }catch (Exception e){
            log.info("deleteBusinessRoute:{}",e.getMessage());
            return new Result<>(false,"系统异常，请稍后重试！");
        }
    }

    /**
     * 更新业务路由
     * @param businessRouteRequest
     * @return
     */
   // @RequiresPermissions("sys:businessRoute:update")
    @RequestMapping(value = "/updateBusinessRoute",method = RequestMethod.POST)
    public Result<Integer> updateBusinessRoute(@RequestBody BusinessRouteRequest businessRouteRequest){
        try {
            if(StringUtils.isEmpty(businessRouteRequest.getNetworkId())){
                return new Result<>(false, ErrorMessageContants.NETWORK_ID_IS_NULL_MSG);
            }
            return businessRouteService.update(businessRouteRequest);
        }catch (Exception e){
            log.info("updateBusinessRoute:{}",e.getMessage());
            return new Result<>(false,"系统异常，请稍后重试！");

        }
    }

    /**
     * 填入接口
     * @param businessRouteRequest
     * @return
     */
    @RequiresPermissions("sys:businessRoute:insert")
    @RequestMapping(value = "/fillBusinessRoute",method = RequestMethod.POST)
    public Result<Integer> fillBusinessRoute(@RequestBody BusinessRouteRequest businessRouteRequest){
        try {
            if(StringUtils.isEmpty(businessRouteRequest.getNetworkId())){
                return new Result<>(false, ErrorMessageContants.NETWORK_ID_IS_NULL_MSG);
            }
            return businessRouteService.update(businessRouteRequest);
        }catch (Exception e){
            log.info("updateBusinessRoute:{}",e.getMessage());
            return new Result<>(false,"系统异常，请稍后重试！");

        }
    }

    /**
     * 查询业务路由
     * @param businessRouteRequest
     * @return
     */
   // @RequiresPermissions("sys:businessRoute:select")
    @RequestMapping(value = "/selectBusinessRoute",method = RequestMethod.POST)
    public Result<PageInfo> selectBusinessRoute(@RequestBody BusinessRouteRequest businessRouteRequest){
        try {
            return businessRouteService.select(businessRouteRequest);
        }catch (Exception e){
            log.info("selectBusinessRoute:{}",e.getMessage());
            return new Result<>(false,"系统异常，请稍后重试！");
        }
    }

    /**
     * 查询基站
     * @param baseStationInfoRequest
     * @return
     */
    @RequestMapping(value = "/selectBaseStation",method = RequestMethod.POST)
    public Result<BaseStationInfoResponse> selectBaseStation(@RequestBody BaseStationInfoRequest baseStationInfoRequest){
        try {
            return businessRouteService.selectBaseStation(baseStationInfoRequest);
        }catch (Exception e){
            log.info("selectBaseStation:{}",e.getMessage());
            return new Result<>(false,"系统异常，请稍后重试！");
        }
    }



}
