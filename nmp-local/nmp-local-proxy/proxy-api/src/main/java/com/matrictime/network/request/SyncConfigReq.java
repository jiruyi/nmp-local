package com.matrictime.network.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SyncConfigReq implements Serializable {

    private static final long serialVersionUID = -6423188845541784864L;

    /**
     * 操作数据范围（1：非全量 2：全量）
     */
    private String editRange;

    /**
     * 设备类型（1:基站 2 分发机 3 生成机 4 缓存机）
     */
    private String deviceType;

    /**
     * 设备id列表（非全量时必输，全量输了也没用）
     */
    private List<String> deviceIds;

    /**
     * 配置id
     */
    private Long configId;
}
