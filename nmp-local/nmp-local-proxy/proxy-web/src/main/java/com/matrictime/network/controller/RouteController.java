package com.matrictime.network.controller;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.NmplBusinessRouteVo;
import com.matrictime.network.modelVo.NmplInternetRouteVo;
import com.matrictime.network.modelVo.NmplStaticRouteVo;
import com.matrictime.network.modelVo.RouteVo;
import com.matrictime.network.service.RouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 路由模块
 * @author hexu
 */
@RestController
@RequestMapping(value = "/route",method = RequestMethod.POST)
@Slf4j
public class RouteController extends SystemBaseService {

    @Autowired
    private RouteService routeService;


    /**
     * 路由插入
     * @param req
     * @return
     */

    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public Result addRoute(@RequestBody RouteVo req){
        Result result;
        try {
            List<RouteVo> voList = new ArrayList<>();
            voList.add(req);
            result = routeService.addRoute(voList);
        }catch (Exception e){
            log.info("RouteController.addRoute{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    /**
     * 路由更新
     * @param req
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Result updateRoute(@RequestBody RouteVo req){
        Result result;
        try {
            result = routeService.updateRoute(req);
        }catch (Exception e){
            log.info("RouteController.updateRoute{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    /**
     * 业务路由插入
     * @param req
     * @return
     */

    @RequestMapping(value = "/addBusinessRoute",method = RequestMethod.POST)
    public Result addBusinessRoute(@RequestBody NmplBusinessRouteVo req){
        Result result;
        try {
            result = routeService.addBusinessRoute(req);
        }catch (Exception e){
            log.info("RouteController.addBusinessRoute{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    /**
     * 业务路由更新
     * @param req
     * @return
     */
    @RequestMapping(value = "/updateBusinessRoute",method = RequestMethod.POST)
    public Result updateBusinessRoute(@RequestBody NmplBusinessRouteVo req){
        Result result;
        try {
            result = routeService.updateBusinessRoute(req);
        }catch (Exception e){
            log.info("RouteController.updateBusinessRoute{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    /**
     * 出网路由插入
     * @param req
     * @return
     */

    @RequestMapping(value = "/addInternetRoute",method = RequestMethod.POST)
    public Result addInternetRoute(@RequestBody NmplInternetRouteVo req){
        Result result;
        try {
            result = routeService.addInternetRoute(req);
        }catch (Exception e){
            log.info("RouteController.addInternetRoute{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    /**
     * 出网路由更新
     * @param req
     * @return
     */
    @RequestMapping(value = "/updateInternetRoute",method = RequestMethod.POST)
    public Result updateInternetRoute(@RequestBody NmplInternetRouteVo req){
        Result result;
        try {
            result = routeService.updateInternetRoute(req);
        }catch (Exception e){
            log.info("RouteController.updateInternetRoute{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    /**
     * 静态路由插入
     * @param req
     * @return
     */

    @RequestMapping(value = "/addStaticRoute",method = RequestMethod.POST)
    public Result addStaticRoute(@RequestBody NmplStaticRouteVo req){
        Result result;
        try {
            result = routeService.addStaticRoute(req);
        }catch (Exception e){
            log.info("RouteController.addStaticRoute{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    /**
     * 路由更新
     * @param req
     * @return
     */
    @RequestMapping(value = "/updateStaticRoute",method = RequestMethod.POST)
    public Result updateStaticRoute(@RequestBody NmplStaticRouteVo req){
        Result result;
        try {
            result = routeService.updateStaticRoute(req);
        }catch (Exception e){
            log.info("RouteController.updateStaticRoute{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }


}