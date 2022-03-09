package com.matrictime.network.service;

import com.matrictime.network.model.DeviceLog;
import com.matrictime.network.model.LoginDetail;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.LogRequest;
import com.matrictime.network.request.LoginRequest;
import com.matrictime.network.response.PageInfo;
import org.springframework.web.bind.annotation.RequestBody;

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

     Result saveDeviceLog( DeviceLog deviceLog);

     Result<PageInfo> queryDeviceLogList(DeviceLog deviceLog);

}
