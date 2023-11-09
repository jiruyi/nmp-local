package com.matrictime.network.controller;

import com.matrictime.network.base.exception.ErrorMessageContants;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.StaticRouteRequest;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.StaticRouteService;
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
 * @date 2022/10/9.
 */
@Slf4j
@RestController
@RequestMapping(value = "/staticRoute",method = RequestMethod.POST)
public class StaticRouteController {

    @Resource
    private StaticRouteService staticRouteService;

    /**
     * 插入静态路由
     * @param staticRouteRequest
     * @return
     */
    //@RequiresPermissions("sys:staticRoute:insert")
    @RequestMapping(value = "/insertStaticRoute",method = RequestMethod.POST)
    public Result<Integer> insertStaticRoute(@RequestBody StaticRouteRequest staticRouteRequest){
        try {
            if(StringUtils.isEmpty(staticRouteRequest.getNetworkId())){
                return new Result<>(false, ErrorMessageContants.NETWORK_ID_IS_NULL_MSG);
            }
            return staticRouteService.insert(staticRouteRequest);
        }catch (Exception e){
            log.info("insertStaticRoute:{}",e.getMessage());
            return new Result<>(false,"系统异常，请稍后重试！");
        }
    }

    /**
     * 删除静态路由
     * @param staticRouteRequest
     * @return
     */
    //@RequiresPermissions("sys:staticRoute:delete")
    @RequestMapping(value = "/deleteStaticRoute",method = RequestMethod.POST)
    public Result<Integer> deleteStaticRoute(@RequestBody StaticRouteRequest staticRouteRequest){
        try {
            if(ObjectUtils.isEmpty(staticRouteRequest.getId())){
                return new Result<>(false, ErrorMessageContants.ID_IS_NULL_MSG);
            }
            return staticRouteService.delete(staticRouteRequest);
        }catch (Exception e){
            log.info("insertStaticRoute:{}",e.getMessage());
            return new Result<>(false,"系统异常，请稍后重试！");
        }
    }

    /**
     * 更新静态路由
     * @param staticRouteRequest
     * @return
     */
    //@RequiresPermissions("sys:staticRoute:update")
    @RequestMapping(value = "/updateStaticRoute",method = RequestMethod.POST)
    public Result<Integer> updateStaticRoute(@RequestBody StaticRouteRequest staticRouteRequest){
        try {
            if(StringUtils.isEmpty(staticRouteRequest.getNetworkId())){
                return new Result<>(false, ErrorMessageContants.NETWORK_ID_IS_NULL_MSG);
            }
//            if(StringUtils.isEmpty(staticRouteRequest.getServerIp()) && StringUtils.isEmpty(staticRouteRequest.getIpV6())){
//                return new Result<>(false,ErrorMessageContants.DEVICE_IP_IS_NULL_MSG);
//            }
            return staticRouteService.update(staticRouteRequest);
        }catch (Exception e){
            log.info("updateStaticRoute:{}",e.getMessage());
            return new Result<>(false,"系统异常，请稍后重试！");
        }
    }

    /**
     * 查询静态路由
     * @param staticRouteRequest
     * @return
     */
    //@RequiresPermissions("sys:staticRoute:select")
    @RequestMapping(value = "/selectStaticRoute",method = RequestMethod.POST)
    public Result<PageInfo> selectStaticRoute(@RequestBody StaticRouteRequest staticRouteRequest){
        try {
            return staticRouteService.select(staticRouteRequest);
        }catch (Exception e){
            log.info("selectStaticRoute:{}",e.getMessage());
            return new Result<>(false,"系统异常，请稍后重试！");
        }
    }
}
