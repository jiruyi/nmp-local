package com.matrictime.network.req;

import lombok.Data;

import java.util.List;

@Data
public class StartServerReq {

    /**
     * 安全服务器id列表
     */
    List<Long> ids;
}
