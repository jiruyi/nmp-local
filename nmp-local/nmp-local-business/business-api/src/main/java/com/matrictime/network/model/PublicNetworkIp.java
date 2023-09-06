package com.matrictime.network.model;

import lombok.Data;

import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/8/31.
 */
@Data
public class PublicNetworkIp {

    /**
     * 通信Ip
     */
    private String communicationIP;

    /**
     *信令端口
     */
    private List<String> signalingPort;

    /**
     *临时端口
     */
    private List<String> ephemeralPort;

    /**
     * 中继端口
     */
    private List<String> trunkPort;


}
