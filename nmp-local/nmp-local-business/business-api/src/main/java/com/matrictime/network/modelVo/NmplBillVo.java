package com.matrictime.network.modelVo;

import lombok.Data;

import java.util.Date;

@Data
public class NmplBillVo {
    /**
     * 主键
     */
    private Long id;

    /**
     * 账单用户id
     */
    private String ownerId;

    /**
     * 流id
     */
    private String streamId;

    /**
     * 消耗流量
     */
    private String flowNumber;

    /**
     * 时长
     */
    private String timeLength;

    /**
     * 消耗密钥量
     */
    private String keyNum;

    /**
     * 杂糅因子
     */
    private String hybridFactor;

    /**
     * 创建时间
     */
    private Date uploadTime;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

}
