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
     * 配置同步范围（1：单条 2：全部）
     */
    private String configRange;

    /**
     * 设备类型（01:接入基站 02:边界基站 11:密钥中心 20:数据采集）
     */
    private String deviceType;

    /**
     * 设备id列表（操作数据范围为非全量时必输，全量输了也没用）
     */
    private List<String> deviceIds;

    /**
     * 配置id（配置同步范围为单条时必输，全部时输了也没用）
     */
    private Long configId;

}
