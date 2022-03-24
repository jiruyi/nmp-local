package com.matrictime.network.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class QuerySignalSelectDeviceIdsResp implements Serializable {
    private static final long serialVersionUID = -3790421765166853698L;
    /**
     * 选项列表
     */
    private List<String> deviceIds;
}
