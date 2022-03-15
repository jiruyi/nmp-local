package com.matrictime.network.response;

import lombok.Data;

import java.io.Serializable;
@Data
public class UploadSingleFileResp implements Serializable {
    private static final long serialVersionUID = -5793698070417382374L;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 文件名称
     */
    private String fileName;
}
