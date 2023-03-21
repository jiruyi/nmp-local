package com.matrictime.network.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
public class VersionLoadReq implements Serializable {
    private static final long serialVersionUID = 7745821562103671169L;

    /**
     * 单个文件
     */
    private MultipartFile file;

    /**
     * 上传文件路径
     */
    private String uploadPath;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件校验值
     */
    private String checkCode;
}
