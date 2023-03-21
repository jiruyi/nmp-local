package com.matrictime.network.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class VersionStartReq implements Serializable {
    private static final long serialVersionUID = 1789476846632554229L;

    /**
     * 上传文件路径
     */
    private String uploadPath;

    /**
     * 文件名称
     */
    private String fileName;


}
