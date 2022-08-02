package com.matrictime.network.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PushVersionFileReq implements Serializable {
    private static final long serialVersionUID = 4303519034935550404L;

    /**
     * 版本文件id
     */
    private Long fileId;

    /**
     * 系统id
     */
    private String systemId;

    /**
     * 设备id列表
     */
    private List<String> deviceIds;
}
