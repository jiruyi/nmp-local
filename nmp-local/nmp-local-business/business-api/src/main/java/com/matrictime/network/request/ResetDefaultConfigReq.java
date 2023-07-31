package com.matrictime.network.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResetDefaultConfigReq implements Serializable {
    private static final long serialVersionUID = -5288412689888863509L;

    /**
     * 操作数据范围（1：非全量 2：全量）
     */
    private String editRange;

    /**
     * id列表（非全量时必输，全量输了也没用）
     */
    private List<Long> ids;

    /**
     * 系统类型 01:接入基站 02:边界基站 11:密钥中心 20:数据采集（全量时必输，非全量输了也没用）
     */
    private String deviceType;
}
