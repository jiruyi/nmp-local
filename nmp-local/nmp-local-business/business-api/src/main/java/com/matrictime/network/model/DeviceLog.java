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

    /**
     * 设备编号
     */
    private String deviceId;
    /**
     * 设备名称
     */
    private String devcieName;
    /**
     * 设备IP
     */
    private String deviceIp;
    /**
     * 上报内容
     */
    private String operDesc;
    /**
     * 设备类型 1 基站 2分发机
     */
    private String deviceType;

    /**
     0 - 一般状态信息/运行时状态
     1 - 重要操作信息
     2 - 需要紧急处理信息
     3 - 系统异常
     */
    private String operType;

    /**
     * 操作人
     */
    private String operUser;
    /**
     * 操作时间
     */
    private String operTime;
}
