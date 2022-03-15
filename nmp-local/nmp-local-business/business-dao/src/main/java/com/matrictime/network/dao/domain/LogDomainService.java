package com.matrictime.network.dao.domain;

import com.matrictime.network.dao.model.NmplDeviceLog;
import com.matrictime.network.dao.model.NmplLoginDetail;
import com.matrictime.network.dao.model.NmplOperateLog;
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
 * @date 2022/3/2 0002 17:11
 * @desc
 */
public interface LogDomainService {

     int saveLog(NmplOperateLog operateLog);

    PageInfo<NmplOperateLog> queryLogList(LogRequest logRequest);

    PageInfo<NmplLoginDetail> queryLoginDetailList(LoginDetail loginDetail);

    int saveDeviceLog(DeviceLog deviceLog);

    PageInfo<NmplDeviceLog> queryDeviceLogList(DeviceLog deviceLog);
}
