package com.matrictime.network.request;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author by wangqiang
 * @date 2023/3/20.
 */
public class UploadSingleFileReq {
    private static final long serialVersionUID = 460668760649310704L;

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
     * 模块名称
     */
    private String moduleName;
}
