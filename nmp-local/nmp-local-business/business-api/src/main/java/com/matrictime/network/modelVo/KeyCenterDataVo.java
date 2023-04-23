package com.matrictime.network.modelVo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author by wangqiang
 * @date 2023/4/20.
 */
@Data
public class KeyCenterDataVo implements Serializable {
    /**
     * 通信负载
     */
    private Double communicationsLoadUp;

    private Double communicationsLoadDown;

    /**
     * 转发负载
     */
    private Double forwardingPayloadUp;

    private Double forwardingPayloadDown;

    /**
     * 密钥分发负载
     */
    private Double KeyDistributionPayloadUp;

    private Double KeyDistributionPayloadDown;
}
