package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.LogRequest;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.LogService;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2022/3/2 0002 16:21
 * @desc
 */
public class LogServiceImpl extends SystemBaseService implements LogService {
    @Override
    public Result<PageInfo> queryNetworkLogList(LogRequest request) {
        return null;
    }
}
