package com.matrictime.network.service.impl;


import com.alibaba.fastjson.JSONArray;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.convert.AlarmInfoConvert;
import com.matrictime.network.convert.LoginLogConvert;
import com.matrictime.network.convert.OperateLogConvert;
import com.matrictime.network.dao.domain.LogDomainService;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.AlarmInfo;
import com.matrictime.network.modelVo.DataPushBody;
import com.matrictime.network.modelVo.LoginDetail;
import com.matrictime.network.modelVo.OperateLog;
import com.matrictime.network.request.AlarmInfoRequest;
import com.matrictime.network.request.LogRequest;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.DataHandlerService;
import com.matrictime.network.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2022/3/2 0002 16:21
 * @desc
 */
@Slf4j
@Service("alarm_info")
public class LogServiceImpl extends SystemBaseService implements LogService, DataHandlerService {

    @Autowired
    private LogDomainService logDomainService;

    @Autowired
    private OperateLogConvert operateLogConvert;

    @Autowired
    private LoginLogConvert loginLogConvert;

    @Autowired
    private  AlarmInfoConvert alarmInfoConvert;



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
     * @title queryAlarmInfoList
     * @param [alarmInfoRequest]
     * @return com.matrictime.network.model.Result<com.matrictime.network.response.PageInfo>
     * @description
     * @author jiruyi
     * @create 2023/8/17 0017 19:43
     */
    @Override
    public Result<PageInfo> queryAlarmInfoList(AlarmInfoRequest alarmInfoRequest) {
        try {
            PageInfo pageInfo = logDomainService.queryAlarmInfoList(alarmInfoRequest);
            List<AlarmInfo> list = alarmInfoConvert.to(pageInfo.getList());
            pageInfo.setList(list);
            return  buildResult(pageInfo);
        }catch (Exception e){
            log.error("queryAlarmInfoList exception :{}",e.getMessage());
            return  failResult(e);
        }
    }

    /**
     * @title handlerData
     * @param [dataPushBody]
     * @return void
     * @description
     * @author jiruyi
     * @create 2023/8/29 0029 15:35
     */
    @Override
    public void handlerData(DataPushBody dataPushBody) {
        try {
            if(ObjectUtils.isEmpty(dataPushBody)){
                return;
            }
            String alarmInfoStr = dataPushBody.getBusiDataJsonStr();
            List<AlarmInfo> alarmInfos = JSONArray.parseArray(alarmInfoStr,AlarmInfo.class);
            if(CollectionUtils.isEmpty(alarmInfos)){
                return;
            }
            log.info("LogServiceImpl handlerData batchInsertAlarmData start count is :{}",alarmInfos.size());
            logDomainService.batchInsertAlarmData(alarmInfos);
        }catch (Exception e){
            log.error("handlerData exception :{}",e);
        }
    }
}
