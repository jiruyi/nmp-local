package com.matrictime.network.request;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/4/26 0026 10:01
 * @desc
 */
@Builder
@Data
public class AlarmDataListReq extends AlarmDataBaseRequest implements Serializable {
    private static final long serialVersionUID = -8676639604701256084L;

    /**告警来源ip*/
    private String alarmSourceIp;
    /**告警级别*/
    private String alarmLevel;
}
