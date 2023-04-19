package com.matrictime.network.dao.domain.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.dao.domain.LogDomainService;
import com.matrictime.network.dao.mapper.NmplDeviceLogMapper;
import com.matrictime.network.dao.mapper.NmplLoginDetailMapper;
import com.matrictime.network.dao.mapper.NmplOperateLogMapper;
import com.matrictime.network.dao.model.NmplDeviceLog;
import com.matrictime.network.dao.model.NmplLoginDetail;
import com.matrictime.network.dao.model.NmplOperateLog;
import com.matrictime.network.model.DeviceLog;
import com.matrictime.network.model.LoginDetail;
import com.matrictime.network.request.LogRequest;
import com.matrictime.network.response.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.BeanUtils;
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

    @Autowired
    private NmplDeviceLogMapper deviceLogMapper;

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

    /**
      * @title saveDeviceLog
      * @param [deviceLog]
      * @return int
      * @description  设备日志保存
      * @author jiruyi
      * @create 2022/3/7 0007 11:11
      */
    @Override
    public int saveDeviceLog(DeviceLog deviceLog) {
        NmplDeviceLog nmplDeviceLog = new NmplDeviceLog();
        BeanUtils.copyProperties(deviceLog,nmplDeviceLog);
        nmplDeviceLog.setDevcieName(deviceLog.getDeviceName());
        return  deviceLogMapper.insertSelective(nmplDeviceLog);
    }

    /**
      * @title queryDeviceLogList
      * @param [deviceLog]
      * @return com.matrictime.network.response.PageInfo<com.matrictime.network.dao.model.NmplDeviceLog>
      * @description 查询设备日志列表
      * @author jiruyi
      * @create 2022/3/7 0007 14:00
      */
    @Override
    public PageInfo<NmplDeviceLog> queryDeviceLogList(DeviceLog deviceLog) {
        Page page = PageHelper.startPage(deviceLog.getPageNo(),deviceLog.getPageSize());
        List<NmplDeviceLog> list = deviceLogMapper.queryDeviceLogList(deviceLog);
        PageInfo<NmplDeviceLog> pageResult =  new PageInfo<>((int)page.getTotal(), page.getPages(), list);
        return pageResult;
    }
}
