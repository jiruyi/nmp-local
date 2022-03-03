package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.convert.OperateLogConvert;
import com.matrictime.network.dao.domain.LogDomainService;
import com.matrictime.network.model.OperateLog;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.LogRequest;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2022/3/2 0002 16:21
 * @desc
 */
@Slf4j
@Service
public class LogServiceImpl extends SystemBaseService implements LogService {

    @Autowired
    private LogDomainService logDomainService;

    @Autowired
    private OperateLogConvert operateLogConvert;

    @Override
    public Result<PageInfo> queryNetworkLogList(LogRequest request) {
        try {
            PageInfo pageInfo = logDomainService.queryLogList(request);
            List<OperateLog> list = operateLogConvert.to(pageInfo.getList());
            pageInfo.setList(list);
            return  buildResult(pageInfo);
        }catch (Exception e){
            log.error("queryNetworkLogList exception :{}",e.getMessage());
            return  failResult(e);
        }
    }
}
