package com.matrictime.network.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class QuerySystemByPageReq extends BaseRequest implements Serializable {

    private static final long serialVersionUID = 3112936037320909769L;

    /**
     * 系统名称
     */
    private String sysName;

    /**
     * 系统类型（1：运营系统 2：运维系统 3:运控系统）
     */
    private String sysType;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;
}
