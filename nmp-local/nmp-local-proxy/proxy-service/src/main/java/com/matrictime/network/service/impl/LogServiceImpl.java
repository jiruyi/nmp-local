package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.dao.domain.LogDomainService;
import com.matrictime.network.model.DeviceLog;
import com.matrictime.network.model.Result;
import com.matrictime.network.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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


    /**
      * @title saveDeviceLog
      * @param deviceLog
      * @return com.matrictime.network.model.Result
      * @description  设备日志保存
      * @author jiruyi
      * @create 2022/3/7 0007 11:05
      */
    @Override
    public Result saveDeviceLog(DeviceLog deviceLog) {
        try {
            int count =  logDomainService.saveDeviceLog(deviceLog);
            return  buildResult(count);
        }catch (Exception e){
            log.error("saveDeviceLog exception :{}",e.getMessage());
            return  failResult(e);
        }
    }


}
