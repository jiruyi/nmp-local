package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.convert.LoginLogConvert;
import com.matrictime.network.convert.OperateLogConvert;
import com.matrictime.network.dao.domain.LogDomainService;
import com.matrictime.network.model.DeviceLog;
import com.matrictime.network.model.LoginDetail;
import com.matrictime.network.model.OperateLog;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.LogRequest;
import com.matrictime.network.request.LoginRequest;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    @Autowired
    private LoginLogConvert loginLogConvert;

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

    /**
     * @title queryLoginDetailList
     * @param [loginDetail]
     * @return com.matrictime.network.model.Result<com.matrictime.network.response.PageInfo>
     * @description
     * @author jiruyi
     * @create 2022/3/7 0007 9:48
     */
    @Override
    public Result<PageInfo> queryLoginDetailList(LoginDetail loginDetail) {
        try {
            PageInfo pageInfo = logDomainService.queryLoginDetailList(loginDetail);
            List<LoginDetail> list = loginLogConvert.to(pageInfo.getList());
            pageInfo.setList(list);
            return  buildResult(pageInfo);
        }catch (Exception e){
            log.error("queryNetworkLogList exception :{}",e.getMessage());
            return  failResult(e);
        }
    }

    /**
      * @title saveDeviceLog
      * @param [deviceLog]
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

    /**
      * @title queryDeviceLogList
      * @param [deviceLog]
      * @return com.matrictime.network.model.Result<com.matrictime.network.response.PageInfo>
      * @description  查询设备日志
      * @author jiruyi
      * @create 2022/3/7 0007 13:45
      */
    @Override
    public Result<PageInfo> queryDeviceLogList(DeviceLog deviceLog) {
        try {
            PageInfo pageInfo = logDomainService.queryDeviceLogList(deviceLog);
            List<LoginDetail> list = loginLogConvert.to(pageInfo.getList());
            pageInfo.setList(list);
            return  buildResult(pageInfo);
        }catch (Exception e){
            log.error("queryNetworkLogList exception :{}",e.getMessage());
            return  failResult(e);
        }
    }


}
