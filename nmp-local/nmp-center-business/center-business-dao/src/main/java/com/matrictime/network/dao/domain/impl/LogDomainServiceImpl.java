package com.matrictime.network.dao.domain.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.dao.domain.LogDomainService;
import com.matrictime.network.dao.mapper.NmplLoginDetailMapper;
import com.matrictime.network.dao.mapper.NmplOperateLogMapper;
import com.matrictime.network.dao.model.NmplLoginDetail;
import com.matrictime.network.dao.model.NmplOperateLog;
import com.matrictime.network.model.LoginDetail;
import com.matrictime.network.request.LogRequest;
import com.matrictime.network.response.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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

    @Autowired
    private NmplLoginDetailMapper loginDetailMapper;


    @Override
    public int saveLog(NmplOperateLog operateLog) {
        if(ObjectUtils.isEmpty(operateLog)){
            return NumberUtils.INTEGER_ZERO;
        }
        return  operateLogMapper.insertSelective(operateLog);
    }

    /**
     * @title queryLogList
     * @param [logRequest]
     * @return com.matrictime.network.response.PageInfo<com.matrictime.network.dao.model.NmplOperateLog>
     * @description
     * @author jiruyi
     * @create 2022/3/4 0004 11:22
     */
    @Override
    public PageInfo<NmplOperateLog> queryLogList(LogRequest logRequest) {
        Page page = PageHelper.startPage(logRequest.getPageNo(),logRequest.getPageSize());
        List<NmplOperateLog> list = operateLogMapper.queryLogList(logRequest);
        PageInfo<NmplOperateLog> pageResult =  new PageInfo<>((int)page.getTotal(), page.getPages(), list);
        return pageResult;
    }

    /**
     * @title queryLoinDetailList
     * @param [loginRequest]
     * @return com.matrictime.network.response.PageInfo<com.matrictime.network.dao.model.NmplLoginDetail>
     * @description 查询登录明细日志
     * @author jiruyi
     * @create 2022/3/4 0004 11:22
     */
    @Override
    public PageInfo<NmplLoginDetail> queryLoginDetailList(LoginDetail loginDetail) {
        Page page = PageHelper.startPage(loginDetail.getPageNo(),loginDetail.getPageSize());
        List<NmplLoginDetail> list = loginDetailMapper.queryLoginDetailList(loginDetail);
        PageInfo<NmplLoginDetail> pageResult =  new PageInfo<>((int)page.getTotal(), page.getPages(), list);
        return pageResult;
    }


}
