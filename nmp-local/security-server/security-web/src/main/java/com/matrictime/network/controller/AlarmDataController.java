package com.matrictime.network.controller;

import com.matrictime.network.dao.model.AcceptAlarmDataReq;
import com.matrictime.network.dao.model.AlarmAndServerInfo;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.PageInfo;
import com.matrictime.network.req.AlarmDataListReq;
import com.matrictime.network.service.AlarmDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/4/19 0019 14:56
 * @desc
 */
@RequestMapping(value = "/alarm")
@Api(value = "告警信息相关接口", tags = "告警信息相关接口")
@RestController
@Slf4j
public class AlarmDataController {

    @Autowired
    private AlarmDataService alarmDataService;

    /**
     * @param []
     * @return void
     * @title acceptAlarmData
     * @description 接受代理数据推送
     * @author jiruyi
     * @create 2023/4/19 0019 14:57
     */
    @ApiOperation(value = "告警信息数据推送", notes = "告警信息数据推送")
    @RequestMapping(value = "/accept", method = RequestMethod.POST)
    public Result acceptAlarmData(@RequestBody AcceptAlarmDataReq req) {
        return alarmDataService.acceptAlarmData(req.getAlarmInfoList(),req.getRedisKey());
    }




    /**
     * @title queryAlarmDataList
     * @param [alarmDataListReq]
     * @return com.matrictime.network.model.Result<com.matrictime.network.modelVo.PageInfo<com.matrictime.network.dao.model.NmpsAlarmInfo>>
     * @description
     * @author jiruyi
     * @create 2023/11/13 0013 14:24
     */
    @ApiOperation(value = "告警信息列表查询", notes = "告警信息列表查询")
    @RequestMapping(value = "/data/list", method = RequestMethod.POST)
    public Result<PageInfo<AlarmAndServerInfo>> queryAlarmDataList(@RequestBody AlarmDataListReq alarmDataListReq) {
        try {
            //setDateTime(alarmDataListReq);
            log.info("AlarmDataController queryAlarmDataList param:{}",alarmDataListReq);
            //查询
            return alarmDataService.queryAlarmDataList(alarmDataListReq);
        } catch (Exception e) {
            log.error("AlarmDataController querySysAlarmDataList exception:{}", e);
            return new Result(false, e.getMessage());
        }
    }

    /**
     * @title setDateTime
     * @param [alarmDataListReq]
     * @return void
     * @description
     * @author jiruyi
     * @create 2023/11/13 0013 18:10
     */
//    public void setDateTime(AlarmDataListReq alarmDataListReq){
//        Date startDate = alarmDataListReq.getStartDateTime();
//        Date endDate = alarmDataListReq.getEndDateTime();
//        if(!ObjectUtils.isEmpty(startDate)){
//            alarmDataListReq.setStartDateTime(DateUtils.formatDateToDate(startDate));
//        }
//        if(!ObjectUtils.isEmpty(endDate)){
//            alarmDataListReq.setStartDateTime(DateUtils.formatDateToDate(endDate));
//        }
//    }


}
