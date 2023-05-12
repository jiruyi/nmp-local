package com.matrictime.network.controller;

import com.matrictime.network.base.SystemException;
import com.matrictime.network.model.AlarmInfo;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.AlarmDataBaseRequest;
import com.matrictime.network.request.AlarmDataListReq;
import com.matrictime.network.response.AlarmDataPhyResp;
import com.matrictime.network.response.AlarmDataSysResp;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.AlarmDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public Result acceptAlarmData(@RequestBody List<AlarmInfo> alarmInfoList) {
        return alarmDataService.acceptAlarmData(alarmInfoList);
    }

    /**
     * @param [dataBaseRequest]
     * @return com.matrictime.network.model.Result
     * @title querySysAlarmDataList
     * @description 根据小区  时间段  查询系统资源
     * @author jiruyi
     * @create 2023/4/24 0024 13:55
     */
    @ApiOperation(value = "系统告警数据统计", notes = "系统告警数据统计")
    @RequestMapping(value = "/system/count", method = RequestMethod.POST)
    public Result<AlarmDataSysResp> querySysAlarmDataCount(@RequestBody AlarmDataBaseRequest dataBaseRequest) {
        try {
            //参数校验
            checkParam(dataBaseRequest);
            log.info("AlarmDataController querySysAlarmDataList param:{}",dataBaseRequest);
            //查询
            return alarmDataService.querySysAlarmDataCount(dataBaseRequest);
        } catch (Exception e) {
            log.error("AlarmDataController querySysAlarmDataList exception:{}", e);
            return new Result(false, e.getMessage());
        }
    }

    /**
      * @title queryPhyAlarmDataList
      * @param [dataBaseRequest]
      * @return com.matrictime.network.model.Result<com.matrictime.network.response.AlarmDataSysResp>
      * @description  根据小区  时间段 查询物理资源告警
      * @author jiruyi
      * @create 2023/4/25 0025 14:38
      */
    @ApiOperation(value = "物理告警数据查询", notes = "物理告警数据查询")
    @RequestMapping(value = "/physical/count", method = RequestMethod.POST)
    public Result<AlarmDataPhyResp> queryPhyAlarmCount(@RequestBody AlarmDataBaseRequest dataBaseRequest) {
        try {
            //参数校验
            checkParam(dataBaseRequest);
            log.info("AlarmDataController queryPhyAlarmDataList param:{}",dataBaseRequest);
            //查询
            return alarmDataService.queryPhyAlarmDataCount(dataBaseRequest);
        } catch (Exception e) {
            log.error("AlarmDataController querySysAlarmDataList exception:{}", e);
            return new Result(false, e.getMessage());
        }
    }


    /**
      * @title queryAlarmDataList
      * @param [alarmDataListReq]
      * @return com.matrictime.network.model.Result<com.matrictime.network.response.PageInfo<com.matrictime.network.model.AlarmInfo>>
      * @description  查询告警信息列表
      * @author jiruyi
      * @create 2023/4/26 0026 15:53
      */
    @ApiOperation(value = "告警信息列表查询", notes = "告警信息列表查询")
    @RequestMapping(value = "/data/list", method = RequestMethod.POST)
    public Result<PageInfo<AlarmInfo>> queryAlarmDataList(@RequestBody AlarmDataListReq alarmDataListReq) {
        try {
            //参数校验
            checkParam(alarmDataListReq);
            log.info("AlarmDataController queryAlarmDataList param:{}",alarmDataListReq);
            //查询
           return alarmDataService.queryAlarmDataList(alarmDataListReq);
        } catch (Exception e) {
            log.error("AlarmDataController querySysAlarmDataList exception:{}", e);
            return new Result(false, e.getMessage());
        }
    }
    /**
     * @param [dataBaseRequest]
     * @return void
     * @title checkParam
     * @description 参数校验
     * @author jiruyi
     * @create 2023/4/24 0024 14:04
     */
    public void checkParam(AlarmDataBaseRequest dataBaseRequest) {
        if (StringUtils.isEmpty(dataBaseRequest.getRoId())) {
            throw new SystemException("小区id不能为空");
        }
        if (StringUtils.isEmpty(dataBaseRequest.getTimePicker())) {
            throw new SystemException("时间范围不能为空");
        }
    }

}
