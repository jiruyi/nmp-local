package com.matrictime.network.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResetDataBusinessConfigResp implements Serializable {

    private static final long serialVersionUID = 3228424536148697115L;
    /**
     * 成功id列表
     */
    List<Long> successIds;

    /**
     * 失败id列表
     */
    List<Long> failIds;
}
