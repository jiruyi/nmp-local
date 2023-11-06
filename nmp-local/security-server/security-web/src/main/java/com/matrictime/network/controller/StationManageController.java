package com.matrictime.network.controller;

import com.matrictime.network.model.Result;
import com.matrictime.network.req.DnsManageRequest;
import com.matrictime.network.req.StationManageRequest;
import com.matrictime.network.service.StationManageService;
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
@RequestMapping(value = "/stationManage")
@RestController
@Slf4j
public class StationManageController {

    @Resource
    private StationManageService stationManageService;

    @RequestMapping(value = "/insertStationManage",method = RequestMethod.POST)
    public Result insertCaManage(@RequestBody StationManageRequest stationManageRequest){
        try {
            Result result = stationManageService.insertStationManage(stationManageRequest);
            return result;
        }catch (Exception e){
            log.error("insertStationManage exception:{}",e.getMessage());
            return new Result(false, e.getMessage());
        }
    }

    @RequestMapping(value = "/selectStationManage",method = RequestMethod.POST)
    public Result selectIpManage(@RequestBody StationManageRequest stationManageRequest){
        try {
            Result result = stationManageService.selectStationManage(stationManageRequest);
            return result;
        }catch (Exception e){
            log.error("selectDnsManage exception:{}",e.getMessage());
            return new Result(false, e.getMessage());
        }
    }

    @RequestMapping(value = "/deleteStationManage",method = RequestMethod.POST)
    public Result deleteIpManage(@RequestBody StationManageRequest stationManageRequest){
        try {
            if(StringUtils.isEmpty(stationManageRequest.getNetworkId())){
                return new Result<>(false,"必传参没传");
            }
            Result result = stationManageService.deleteStationManage(stationManageRequest);
            return result;
        }catch (Exception e){
            log.error("deleteStationManage exception:{}",e.getMessage());
            return new Result(false, e.getMessage());
        }
    }

    @RequestMapping(value = "/updateStationManage",method = RequestMethod.POST)
    public Result updateIpManage(@RequestBody StationManageRequest stationManageRequest){
        try {
            if(StringUtils.isEmpty(stationManageRequest.getNetworkId()) ||
                    StringUtils.isEmpty(stationManageRequest.getId())){
                return new Result<>(false,"必传参没传");
            }
            Result result = stationManageService.updateStationManage(stationManageRequest);
            return result;
        }catch (Exception e){
            log.error("updateStationManage exception:{}",e.getMessage());
            return new Result(false, e.getMessage());
        }
    }


}
