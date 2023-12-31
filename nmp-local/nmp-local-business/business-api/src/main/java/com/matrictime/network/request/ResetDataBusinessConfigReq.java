package com.matrictime.network.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResetDataBusinessConfigReq implements Serializable {

    private static final long serialVersionUID = -1371604359499355984L;
    /**
     * 操作数据范围（1：非全量 2：全量）
     */
    private String editRange;

    /**
     * id列表（非全量时必输，全量输了也没用）
     */
    private List<Long> ids;
}
