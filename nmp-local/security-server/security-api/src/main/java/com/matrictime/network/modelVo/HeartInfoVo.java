package com.matrictime.network.modelVo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class HeartInfoVo implements Serializable {

    private static final long serialVersionUID = 4878826406091803392L;
    /**
     * 入网id
     */
    private String networkId;

    /**
     * 状态 1:正常
     */
    private Short serverStatus;

    /**
     * 创建时间
     */
    private Date createTime;
}
