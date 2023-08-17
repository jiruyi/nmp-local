package com.matrictime.network.dao.domain;

import com.matrictime.network.dao.model.NmplLoginDetail;
import com.matrictime.network.dao.model.NmplOperateLog;
import com.matrictime.network.modelVo.LoginDetail;
import com.matrictime.network.request.AlarmInfoRequest;
import com.matrictime.network.request.LogRequest;
import com.matrictime.network.response.PageInfo;

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

    PageInfo queryAlarmInfoList(AlarmInfoRequest alarmInfoRequest);
}
