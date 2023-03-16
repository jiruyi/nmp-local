package com.matrictime.network.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class VersionStartReq implements Serializable {
    private static final long serialVersionUID = 1789476846632554229L;

    /**
     * 版本文件Id
     */
    private Long versionFileId;

}
