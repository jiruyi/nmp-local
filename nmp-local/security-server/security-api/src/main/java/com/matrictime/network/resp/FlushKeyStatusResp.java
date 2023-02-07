package com.matrictime.network.resp;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class FlushKeyStatusResp implements Serializable {

    private static final long serialVersionUID = -1071967561823779678L;

    /**
     * 操作状态（9999：初始状态 1000：待执行 1001：执行结束）
     */
    private Short operateStatus;

    /**
     * 操作进度（0~100）
     */
    private Short operateRate;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
