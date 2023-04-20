package com.matrictime.network.facade;

import com.matrictime.network.model.AlarmInfo;
import com.matrictime.network.model.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/4/19 0019 16:37
 * @desc 告警数据接口
 */
@FeignClient(value = "nmp-local-business",path = "nmp-local-business")
public interface AlarmDataFacade {
    @RequestMapping(value= "/alarm/accept",method = RequestMethod.POST)
    Result acceptAlarmData(List<AlarmInfo> alarmInfoList);
}
