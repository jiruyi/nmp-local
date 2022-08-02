package com.matrictime.network.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CleanSignalResp implements Serializable {
    private static final long serialVersionUID = 4462842537347821874L;

    /**
     * 开启追踪的设备id列表
     */
    private List<String> usedIds;
}
