package com.matrictime.network.controller;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.StationSummaryRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author by wangqiang
 * @date 2023/8/14.
 */


@RequestMapping(value = "/companyStationSummary")
@RestController
@Slf4j
public class CompanyStationSummaryController {

    /**
     * 单个小区基站数量、网络总数查询
     * @param stationSummaryRequest
     * @return
     */
    @RequestMapping(value = "/selectCompanyStationSummary")
    public Result selectCompanyStationSummary(@RequestBody StationSummaryRequest stationSummaryRequest){
        if(!StringUtils.isEmpty(stationSummaryRequest.getCompanyNetworkId())){
            return new Result<>(false,"缺少必传参数");
        }
        if(!StringUtils.isEmpty(stationSummaryRequest.getSumType())){
            return new Result<>(false,"缺少必传参数");
        }


        return null;
    }


}
