package com.matrictime.network.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/4/24 0024 10:58
 * @desc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlarmDataBaseRequest extends BaseRequest implements Serializable {

    /**小区id*/
    private String  roId;

    /**0 :当天（从0点开始计时），3:近3天，7:近7天，30:近1个月，90:近3个月，180:近6个月*/
    private String  timePicker;
}
