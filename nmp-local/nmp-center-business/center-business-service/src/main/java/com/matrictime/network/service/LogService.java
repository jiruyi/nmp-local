package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.LoginDetail;
import com.matrictime.network.request.AlarmInfoRequest;
import com.matrictime.network.request.LogRequest;
import com.matrictime.network.response.PageInfo;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2022/3/2 0002 16:20
 * @desc
 */
public interface LogService {

     Result<PageInfo> queryNetworkLogList(LogRequest request);

     Result<PageInfo> queryLoginDetailList(LoginDetail loginDetail);

     Result<PageInfo> queryAlarmInfoList(AlarmInfoRequest alarmInfoRequest);

}
