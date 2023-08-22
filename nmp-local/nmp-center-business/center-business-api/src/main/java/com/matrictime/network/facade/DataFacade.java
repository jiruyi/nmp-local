package com.matrictime.network.facade;

import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.StationSummaryVo;
import com.matrictime.network.response.CompanyHeartbeatResponse;
import com.matrictime.network.response.CompanyInfoResponse;
import com.matrictime.network.response.DataCollectResponse;
import com.matrictime.network.response.TerminalUserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author by wangqiang
 * @date 2023/8/21.
 */
@FeignClient(value = "nmp-center-business",path = "nmp-center-business",contextId = "facade")
public interface DataFacade {

    /**
     * 流量数据收集
     * @param req
     * @return
     */
    @RequestMapping(value= "/dataCollect/insertDataCollect",method = RequestMethod.POST)
    Result insertDataCollect(@RequestBody DataCollectResponse req);


    /**
     * 小区心跳上报
     * @param req
     * @return
     */
    @RequestMapping(value= "/companyHeartbeat/insertCompanyHeartbeat",method = RequestMethod.POST)
    Result insertCompanyHeartbeat(@RequestBody CompanyHeartbeatResponse req);

    /**
     * 小区信息上报
     * @param req
     * @return
     */
    @RequestMapping(value= "/company/receiveCompany",method = RequestMethod.POST)
    Result receiveCompany(@RequestBody CompanyInfoResponse req);

    /**
     * 小区各种类型基站上报
     * @param req
     * @return
     */
    @RequestMapping(value= "/companyStationSummary/receiveStationSummary",method = RequestMethod.POST)
    Result receiveStationSummary(@RequestBody StationSummaryVo req);

    /**
     * 终端用户上报
     * @param req
     * @return
     */
    @RequestMapping(value= "/companyTerminalUser/receiveTerminalUser",method = RequestMethod.POST)
    Result receiveTerminalUser(@RequestBody TerminalUserResponse req);


}
