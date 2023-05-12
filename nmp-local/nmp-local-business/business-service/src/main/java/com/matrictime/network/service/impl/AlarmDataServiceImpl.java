package com.matrictime.network.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.enums.AlarmSysLevelEnum;
import com.matrictime.network.convert.AlarmInfoConvert;
import com.matrictime.network.dao.domain.AlarmDataDomainService;
import com.matrictime.network.dao.model.NmplAlarmInfo;
import com.matrictime.network.model.AlarmInfo;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.AlarmDataBaseRequest;
import com.matrictime.network.request.AlarmDataListReq;
import com.matrictime.network.response.*;
import com.matrictime.network.service.AlarmDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    private AlarmDataDomainService alarmDataDomainService;

    @Autowired
    private AlarmInfoConvert alarmInfoConvert;

    /**
     * @param [alarmInfoList]
     * @return com.matrictime.network.model.Result
     * @title acceptAlarmData
     * @description 告警信息入库
     * @author jiruyi
     * @create 2023/4/19 0019 16:22
     */
    @Override
    public Result acceptAlarmData(List<AlarmInfo> alarmInfoList,String ip) {
        //参数校验
        if (CollectionUtils.isEmpty(alarmInfoList)) {
            return failResult("acceptAlarmData alarmInfoList is null");
        }
        try {
            //插入数据库
            log.info("alarmInfoList param size is: {}", alarmInfoList.size());
            int count = alarmDataDomainService.acceptAlarmData(alarmInfoList,ip);
            log.info("alarmDataDomainService save mysql  count is: {}", count);
            return buildResult(count);
        } catch (Exception e) {
            log.error("AlarmDataService acceptAlarmData exception : {} ", e);
            return failResult(e);
        }

    }

    /**
     * @param [alarmDataBaseRequest]
     * @return com.matrictime.network.model.Result
     * @title querySysAlarmDataList
     * @description 系统告警查询 各个条数
     * @author jiruyi
     * @create 2023/4/24 0024 13:57
     */
    @Override
    public Result<AlarmDataSysResp> querySysAlarmDataCount(AlarmDataBaseRequest alarmDataBaseRequest) {
        Result<AlarmDataSysResp> result = null;
        try {
            //结果map   // {"access":{"seriousCount":2,"emergentCount":14,"3":27},"sameAsCount":{"1":2,"2":14,"3":27}}
            Map<String, Map<String, Long>> domainMap =
                    alarmDataDomainService.querySysAlarmDataCount(alarmDataBaseRequest);
            if (CollectionUtils.isEmpty(domainMap)) {
                return buildResult(new AlarmDataSysResp());
            }
            // 结果map to bean
            AlarmDataSysResp sysResp =  new AlarmDataSysResp();
            ObjectMapper objectMapper = new ObjectMapper();
            for(AlarmSysLevelEnum sysLevelEnum : AlarmSysLevelEnum.values()){
                AlarmSysLevelCount resp = objectMapper.convertValue(domainMap.get(sysLevelEnum.getType()),AlarmSysLevelCount.class);
                if(Objects.isNull(resp)){
                    resp = new AlarmSysLevelCount();
                }
                switch (sysLevelEnum){
                    case ACCESS:sysResp.setAccessCountResp(resp);break;
                    case BOUNDARY: sysResp.setBoundaryCountResp(resp);break;
                    case KEYCENTER: sysResp.setKeyCenterCountResp(resp);break;
                    default:
                }
            }
            result = buildResult(sysResp);
            log.info("querySysAlarmData 结果map to bean  AlarmDataSysResp:{}",sysResp);
        } catch (Exception e) {
            log.error("AlarmDataService querySysAlarmData exception : {} ", e.getMessage());
            result = failResult(e);
        }
        return result;
    }



    /**
     * @title queryPhyAlarmData
     * @param [alarmDataBaseRequest]
     * @return com.matrictime.network.model.Result<com.matrictime.network.response.AlarmDataPhyResp>
     * @description  查询物理设备资源告警
     * @author jiruyi
     * @create 2023/4/25 0025 15:01
     */
    @Override
    public Result<AlarmDataPhyResp> queryPhyAlarmDataCount(AlarmDataBaseRequest alarmDataBaseRequest) {
        AlarmDataPhyResp alarmDataPhyResp = null;
        try {
            List<AlarmPhyTypeCount> phyTypeCountList = alarmDataDomainService.queryPhyAlarmDataCount(alarmDataBaseRequest);
            alarmDataPhyResp = AlarmDataPhyResp.builder().phyTypeCountList(phyTypeCountList).build();
            return buildResult(alarmDataPhyResp);
        }catch (Exception e){
            log.error("AlarmDataService queryPhyAlarmData exception : {} ", e.getMessage());
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
    public Result<PageInfo<AlarmInfo>> queryAlarmDataList(AlarmDataListReq alarmDataListReq) {
        PageInfo<AlarmInfo> alarmInfoPageInfo =  null;
        try {
            PageInfo<NmplAlarmInfo>  nmplPageInfo= alarmDataDomainService.queryAlarmDataList(alarmDataListReq);
            //bean转换
            List<AlarmInfo>  alarmInfoList = alarmInfoConvert.to(nmplPageInfo.getList());
            alarmInfoPageInfo = new PageInfo<>(nmplPageInfo.getCount(),nmplPageInfo.getPages(),alarmInfoList);
            return buildResult(alarmInfoPageInfo);
        }catch (Exception e){
            log.error("AlarmDataService queryAlarmDataList exception : {} ", e);
            return failResult(e);
        }
    }
}
