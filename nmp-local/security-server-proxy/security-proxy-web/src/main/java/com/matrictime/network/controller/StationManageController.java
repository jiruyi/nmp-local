package com.matrictime.network.controller;

import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.StationManageVo;
import com.matrictime.network.service.StationManageService;
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
@RequestMapping(value = "/stationManage",method = RequestMethod.POST)
@Slf4j
public class StationManageController {

    @Resource
    private StationManageService stationManageService;

    @RequestMapping(value = "/insertStationManage",method = RequestMethod.POST)
    public Result insertCaManage(@RequestBody StationManageVo stationManageVo){
        try {
            Result result = stationManageService.insertStationManage(stationManageVo);
            return result;
        }catch (Exception e){
            log.error("insertStationManage exception:{}",e.getMessage());
            return new Result(false, e.getMessage());
        }
    }

    @RequestMapping(value = "/deleteStationManage",method = RequestMethod.POST)
    public Result deleteStationManage(@RequestBody StationManageVo stationManageVo){
        try {
            Result result = stationManageService.deleteStationManage(stationManageVo);
            return result;
        }catch (Exception e){
            log.error("deleteStationManage exception:{}",e.getMessage());
            return new Result(false, e.getMessage());
        }
    }

}
