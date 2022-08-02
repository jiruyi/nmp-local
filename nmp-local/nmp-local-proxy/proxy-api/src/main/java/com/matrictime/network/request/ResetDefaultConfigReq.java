package com.matrictime.network.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResetDefaultConfigReq implements Serializable {
    private static final long serialVersionUID = -5288412689888863509L;

    /**
     * 操作数据范围（1：非全量 3：全量）
     */
    private String editRange;

    /**
     * id列表（非全量时必输，全量输了也没用）
     */
    private List<Long> ids;
}
