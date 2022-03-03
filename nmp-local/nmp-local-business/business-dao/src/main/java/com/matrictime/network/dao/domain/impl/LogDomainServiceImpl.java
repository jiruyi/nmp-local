package com.matrictime.network.dao.domain.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.dao.domain.LogDomainService;
import com.matrictime.network.dao.mapper.NmplOperateLogMapper;
import com.matrictime.network.dao.model.NmplLoginDetail;
import com.matrictime.network.dao.model.NmplOperateLog;
import com.matrictime.network.dao.model.NmplUser;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.LogRequest;
import com.matrictime.network.response.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2022/3/2 0002 17:12
 * @desc
 */
@Slf4j
@Service
public class LogDomainServiceImpl extends SystemBaseService implements LogDomainService {

    @Autowired
    private NmplOperateLogMapper operateLogMapper;

    @Override
    public PageInfo<NmplOperateLog> queryLogList(LogRequest logRequest) {
        Page page = PageHelper.startPage(logRequest.getPageNo(),logRequest.getPageSize());
        List<NmplOperateLog> list = operateLogMapper.queryLogList(logRequest);
        PageInfo<NmplOperateLog> pageResult =  new PageInfo<>((int)page.getTotal(), page.getPages(), list);
        return pageResult;
    }
}
