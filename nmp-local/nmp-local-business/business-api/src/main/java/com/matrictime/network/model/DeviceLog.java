package com.matrictime.network.model;

import com.matrictime.network.request.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2022/3/7 0007 10:56
 * @desc
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeviceLog  extends BaseRequest {

    private String deviceId;

    private String deviceName;

    private String deviceIp;

    private String operDesc;
    /**
     * 设备类型
     */
    private String deviceType;

    /**
     0 - 一般状态信息/运行时状态
     1 - 重要操作信息
     2 - 需要紧急处理信息
     3 - 系统异常
     */
    private String operType;

    private String operUser;

    private String operTime;
}
