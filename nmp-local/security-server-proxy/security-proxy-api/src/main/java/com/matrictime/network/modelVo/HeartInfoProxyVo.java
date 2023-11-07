package com.matrictime.network.modelVo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class HeartInfoProxyVo implements Serializable {

    private static final long serialVersionUID = -7144234387224297973L;

    /**
     * 入网id
     */
    private String networkId;

    /**
     * 状态 0：内外网正常 1：外网异常
     */
    private Short serverStatus;

    /**
     * 创建时间
     */
    private Date createTime;
}
