package com.matrictime.network.controller;

import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.StationSummaryVo;
import com.matrictime.network.request.StationSummaryRequest;
import com.matrictime.network.service.StationSummaryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author by wangqiang
 * @date 2023/8/14.
 */


@RequestMapping(value = "/companyStationSummary")
@RestController
@Slf4j
public class CompanyStationSummaryController {

    @Resource
    private StationSummaryService stationSummaryService;


    @RequestMapping(value = "/receiveStationSummary")
    public Result receiveStationSummary(@RequestBody StationSummaryVo stationSummaryVo){
        Result result = new Result<>();
        try {
            if(ObjectUtils.isEmpty(stationSummaryVo)){
                return new Result(false,"上部数据为空");
            }
            result = stationSummaryService.receiveStationSummary(stationSummaryVo);
        }catch (Exception e){
            log.info("receiveStationSummary:{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
        }
        return result;
    }


}
