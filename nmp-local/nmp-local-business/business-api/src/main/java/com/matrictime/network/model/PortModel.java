package com.matrictime.network.model;

import lombok.Data;

import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/9/5.
 */
@Data
public class PortModel {
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
