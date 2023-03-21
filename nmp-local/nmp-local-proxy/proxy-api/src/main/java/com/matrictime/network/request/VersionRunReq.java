package com.matrictime.network.request;

import lombok.Data;

import java.io.Serializable;
@Data
public class VersionRunReq implements Serializable {
    private static final long serialVersionUID = 460668760649310704L;

    /**
     * 上传文件路径
     */
    private String uploadPath;

    /**
     * 文件名称
     */
    private String fileName;
}
