package com.matrictime.network.modelVo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class DeviceReportBusinessVo implements Serializable {


    private static final long serialVersionUID = -1939686594152745528L;
    /**
     * 主键
     */
    private Long id;

    /**
     * 设备id
     */
    private String deviceId;

    /**
     * 业务名称
     */
    private String businessName;

    /**
     * 业务编码
     */
    private String businessCode;

    /**
     * 业务配置值
     */
    private String businessValue;

    /**
     * 默认值
     */
    private String defaultValue;

    /**
     * 上报时间
     */
    private Date reportTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 状态 true:存在(1)  false:删除(0)
     */
    private Boolean isExist;
}
