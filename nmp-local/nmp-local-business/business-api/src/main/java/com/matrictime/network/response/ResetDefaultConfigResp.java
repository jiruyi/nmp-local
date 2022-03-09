package com.matrictime.network.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
public class ResetDefaultConfigResp implements Serializable {
    private static final long serialVersionUID = 8180685150285589004L;

    /**
     * 成功id列表
     */
    List<Long> successIds;

    /**
     * 失败id列表
     */
    List<Long> failIds;
}
