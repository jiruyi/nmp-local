package com.matrictime.network.modelVo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class NmplDeviceTaskConfigVo  implements Serializable {

    private static final long serialVersionUID = -4895454737643938348L;
    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 业务名称
     */
    private String taskName;

    /**
     * 设备id
     */
    private String deviceId;

    /**
     * 设备类型 01:接入基站 02:边界基站 11:密钥中心 12:生成机 13:缓存机 20:采集设备 21:指控中心
     */
    private String deviceType;

    /**
     * 任务类型 01:定时任务 02:周期任务
     */
    private String taskType;

    /**
     * 当前值
     */
    private Long currentValue;

    /**
     * 默认值
     */
    private Long defaultValue;

    /**
     * 启停 1启动 0 停止
     */
    private Boolean status;

    /**
     * 备注
     */
    private String remark;

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
