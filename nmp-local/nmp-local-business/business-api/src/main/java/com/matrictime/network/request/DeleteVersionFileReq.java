package com.matrictime.network.request;

import lombok.Data;

import java.io.Serializable;
@Data
public class DeleteVersionFileReq implements Serializable {
    private static final long serialVersionUID = -7553820013788774512L;

    /**
     * 版本文件id
     */
    private Long versionFileId;
}
