package com.matrictime.network.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class QueryTaskReq extends BaseRequest implements Serializable {

    private static final long serialVersionUID = 2498838169857703072L;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 网元类型 01:接入基站 02:边界基站 11:密钥中心 12:生成机 13:缓存机 20:采集设备 21:指控中心
     */
    private String deviceType;

    /**
     * 任务类型 01:定时任务 02:周期任务
     */
    private String taskType;
}
