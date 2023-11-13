package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.dao.domain.AlarmDomainService;
import com.matrictime.network.dao.model.AlarmAndServerInfo;
import com.matrictime.network.dao.model.NmpsAlarmInfo;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.PageInfo;
import com.matrictime.network.req.AlarmDataListReq;
import com.matrictime.network.service.AlarmDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/4/19 0019 16:21
 * @desc 告警信息 服务
 */
@Service
@Slf4j
public class AlarmDataServiceImpl extends SystemBaseService implements AlarmDataService {

    @Autowired
    private AlarmDomainService alarmDomainService;

    /**
     * @param [alarmInfoList]
     * @return com.matrictime.network.model.Result
     * @title acceptAlarmData
     * @description 告警信息入库
     * @author jiruyi
     * @create 2023/4/19 0019 16:22
     */
    @Override
    public Result acceptAlarmData(List<NmpsAlarmInfo> alarmInfoList, String redisKey) {
        //参数校验
        if (CollectionUtils.isEmpty(alarmInfoList)) {
            return failResult("acceptAlarmData alarmInfoList is null");
        }
        try {
            //插入数据库
            log.info("alarmInfoList param size is: {}", alarmInfoList.size());
            int count = alarmDomainService.acceptAlarmData(alarmInfoList,redisKey);
            log.info("alarmDataDomainService save mysql  count is: {}", count);
            return buildResult(count);
        } catch (Exception e) {
            log.error("AlarmDataService acceptAlarmData exception : {} ", e);
            return failResult(e);
        }

    }



    /**
     * @title queryAlarmDataList
     * @param [alarmDataBaseRequest]
     * @return com.matrictime.network.model.Result<com.matrictime.network.response.PageInfo<com.matrictime.network.model.AlarmInfo>>
     * @description  查询告警list
     * @author jiruyi
     * @create 2023/4/26 0026 10:09
     */
    @Override
    public Result<PageInfo<AlarmAndServerInfo>> queryAlarmDataList(AlarmDataListReq alarmDataListReq) {
        try {
            PageInfo<AlarmAndServerInfo> pageInfo  = alarmDomainService.queryAlarmDataList(alarmDataListReq);
            return buildResult(pageInfo);
        }catch (Exception e){
            log.error("AlarmDataService queryAlarmDataList exception : {} ", e);
            return failResult(e);
        }
    }
}
