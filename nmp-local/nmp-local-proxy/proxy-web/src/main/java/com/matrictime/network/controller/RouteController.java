package com.matrictime.network.controller;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.model.Result;
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
            List<RouteVo> voList = new ArrayList<>();
            voList.add(req);
            result = routeService.updateRoute(voList);
        }catch (Exception e){
            log.info("RouteController.updateRoute{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }


}