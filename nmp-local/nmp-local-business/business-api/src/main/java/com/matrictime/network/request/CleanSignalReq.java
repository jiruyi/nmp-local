package com.matrictime.network.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CleanSignalReq implements Serializable {
    private static final long serialVersionUID = -421866674154994785L;

    /**
     * 设备id列表
     */
    private List<String> deviceIds;
}
