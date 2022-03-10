package com.matrictime.network.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2022/3/2 0002 16:23
 * @desc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogRequest extends BaseRequest {

    /**
     * 操作人id
     */
    private String operUserId;
    /**
     * 操作人名字
     */
    private String operUserName;
    /**
     * 操作时间
     */
    private String operDate;



}
