package com.matrictime.network.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ExportSignalReq implements Serializable {
    private static final long serialVersionUID = -52125628128842642L;

    /**
     * 设备id列表
     */
    private List<String> deviceIds;
}
