package com.matrictime.network.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/11/13 0013 14:02
 * @desc
 */
@Data
@ApiModel(description = "告警入参")
public class AlarmDataListReq extends BaseRequest {

    /**
     * 级别 1严重 2 紧急 3 一般
     */
    private String alarmLevel;

    /**
     * 入网id
     */
    private String networkId;
    /**
     * ip
     */
    private String comIp;

    /**
     * 服务名
     */
    private String serverName;

    /**
     * 开始时间 yyyy-MM-dd hh:mm:ss
     */
    private String startDateTimeStr;
    /**
     * 结束时间 yyyy-MM-dd hh:mm:ss
     */
    private String endDateTimeStr;

    /**
     * 开始时间 日期
     */
    @ApiModelProperty(value = "开始时间  yyyy-MM-dd hh:mm:ss")
    private Date startDateTime;
    /**
     * 结束时间 日期
     */
    @ApiModelProperty(value = "结束时间  yyyy-MM-dd hh:mm:ss")
    private Date endDateTime;

}
