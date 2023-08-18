package com.matrictime.network.request;

import lombok.Data;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/8/17 0017 17:23
 * @desc
 */
@Data
public class AlarmInfoRequest extends BaseRequest{

    private  String alarmAreaCode;
}
