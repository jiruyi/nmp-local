package com.matrictime.network.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SyncConfigResp implements Serializable {
    private static final long serialVersionUID = -1562495784064023201L;

    /**
     * 成功id列表
     */
    List<String> successIds;

    /**
     * 失败id列表
     */
    List<String> failIds;
}
