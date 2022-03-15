package com.matrictime.network.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class QueryVersionFileDetailReq implements Serializable {
    private static final long serialVersionUID = 2451451224521578219L;

    /**
     * 版本文件主键
     */
    private Long versionFileId;
}
