package com.matrictime.network.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class FileIsExistReq implements Serializable {
    private static final long serialVersionUID = -1616692961107463418L;

    /**
     * 上传文件路径
     */
    private String uploadPath;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 模块名称
     */
    private String moduleName;
}
