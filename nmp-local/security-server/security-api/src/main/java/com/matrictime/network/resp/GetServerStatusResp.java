package com.matrictime.network.resp;

import lombok.Data;

import java.io.Serializable;

@Data
public class GetServerStatusResp implements Serializable {

    private static final long serialVersionUID = 3592791088343981162L;

    /**
     * 安全服务器状态
     */
    private String status;
}
