package com.matrictime.network.dao.domain;

import com.matrictime.network.model.DeviceLog;

/**
 * @copyright www.matrictime.com
 * @project nmp-local
 * @desc 日志
 */
public interface LogDomainService {

    /**
     * 日志保存
     * @param deviceLog
     * @return int
     */
    int saveDeviceLog(DeviceLog deviceLog);

}
