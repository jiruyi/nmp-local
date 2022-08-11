package com.matrictime.network.controller;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.AddBaseStationInfoRequest;
import com.matrictime.network.request.UpdateBaseStationInfoRequest;
import com.matrictime.network.service.BaseStationInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 基站管理模块
 * @author hexu
 */
@RestController
@RequestMapping(value = "/baseStation",method = RequestMethod.POST)
@Slf4j
public class BaseStationController extends SystemBaseService {

    @Autowired
    private BaseStationInfoService baseStationInfoService;


    /**
     * 基站插入
     * @param request
     * @return
     */

    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public Result addBaseStationInfo(@RequestBody AddBaseStationInfoRequest request){
        Result result;
        try {
            result = baseStationInfoService.addBaseStationInfo(request.getInfoVos());
        }catch (Exception e){
            log.info("BaseStationController.addBaseStationInfo{}",e.getMessage());
            result = failResult(e);
        }
        return result;
    }

    /**
     * 基站更新
     * @param request
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Result updateBaseStationInfo(@RequestBody UpdateBaseStationInfoRequest request){
        Result result;
        try {
            result = baseStationInfoService.updateBaseStationInfo(request.getInfoVos());
        }catch (Exception e){
            log.info("BaseStationController.updateBaseStation{}",e.getMessage());
            result = failResult(e);
        }
        return result;
    }

    /**
     * 基站删除
     * @param request
     * @return
     */
//    @RequestMapping(value = "/delete",method = RequestMethod.POST)
//    public Result deleteBaseStation(@RequestBody DeleteBaseStationInfoRequest request){
//        Result result;
//        try {
//            result = baseStationInfoService.deleteBaseStationInfo(request);
//        }catch (Exception e){
//            log.info("BaseStationController:deleteBaseStation{}",e.getMessage());
//            result = failResult(e);
//        }
//        return result;
//    }

}