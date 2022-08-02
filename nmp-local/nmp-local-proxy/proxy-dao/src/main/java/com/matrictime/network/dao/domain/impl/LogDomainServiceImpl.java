package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.dao.domain.LogDomainService;
import com.matrictime.network.dao.mapper.NmplDeviceLogMapper;
import com.matrictime.network.dao.model.NmplDeviceLog;
import com.matrictime.network.model.DeviceLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @copyright www.matrictime.com
 * @project nmp-local
 * @desc 日志
 */
@Slf4j
@Service
public class LogDomainServiceImpl extends SystemBaseService implements LogDomainService {

    @Autowired(required = false)
    private NmplDeviceLogMapper deviceLogMapper;


    /**
      * @title saveDeviceLog
      * @param deviceLog
      * @return int
      * @description  设备日志保存
      */
    @Override
    public int saveDeviceLog(DeviceLog deviceLog) {
        NmplDeviceLog nmplDeviceLog = new NmplDeviceLog();
        BeanUtils.copyProperties(deviceLog,nmplDeviceLog);
        nmplDeviceLog.setDevcieName(deviceLog.getDeviceName());
        return  deviceLogMapper.insertSelective(nmplDeviceLog);
    }
}
