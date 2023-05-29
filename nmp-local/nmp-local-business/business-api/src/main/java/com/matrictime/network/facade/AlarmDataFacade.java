package com.matrictime.network.facade;

import com.matrictime.network.model.AlarmInfo;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.*;
import com.matrictime.network.response.SystemHeartbeatResponse;
import com.matrictime.network.response.TerminalUserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/4/19 0019 16:37
 * @desc 告警数据接口
 */
@FeignClient(value = "nmp-local-business",path = "nmp-local-business",contextId = "alarm")
public interface AlarmDataFacade {
    @RequestMapping(value= "/alarm/accept",method = RequestMethod.POST)
    Result acceptAlarmData(@RequestBody List<AlarmInfo> alarmInfoList, @RequestParam("ip") String ip);

    @RequestMapping(value= "/monitor/checkHeart",method = RequestMethod.POST)
    Result checkHeart(@RequestBody CheckHeartReq req);

    @RequestMapping(value= "/monitor/physicalDeviceHeartbeat",method = RequestMethod.POST)
    Result physicalDeviceHeartbeat(PhysicalDeviceHeartbeatReq req);

    @RequestMapping(value= "/monitor/physicalDeviceResource",method = RequestMethod.POST)
    Result physicalDeviceResource(PhysicalDeviceResourceReq req);

    @RequestMapping(value= "/monitor/systemResource",method = RequestMethod.POST)
    Result systemResource(SystemResourceReq req);

    @RequestMapping(value= "/systemDataCollect/insertSystemData",method = RequestMethod.POST)
    Result insertSystemData(DataCollectReq dataCollectReq);

    @RequestMapping(value= "/systemHeartbeat/updateSystemHeartbeat",method = RequestMethod.POST)
    Result systemHeartbeatResource(SystemHeartbeatResponse systemHeartbeatResponse);

    @RequestMapping(value= "/terminalUser/updateTerminalUser",method = RequestMethod.POST)
    Result terminalUserResource(TerminalUserResponse terminalUserResponse);

    @RequestMapping(value= "/terminalData/collectTerminalData",method = RequestMethod.POST)
    Result collectTerminalDataResource(TerminalDataListRequest terminalDataListRequest);

}
