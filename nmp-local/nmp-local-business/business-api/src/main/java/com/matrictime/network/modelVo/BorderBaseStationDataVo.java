package com.matrictime.network.modelVo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author by wangqiang
 * @date 2023/4/20.
 */
@Data
public class BorderBaseStationDataVo implements Serializable {

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
     *密钥中继
     */
    private Double keyRelayPayloadUp;

    private Double keyRelayPayloadDown;
}
