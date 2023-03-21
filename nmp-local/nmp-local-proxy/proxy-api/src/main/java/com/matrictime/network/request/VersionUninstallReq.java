package com.matrictime.network.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
public class VersionUninstallReq implements Serializable {
    private static final long serialVersionUID = -5300073025540384824L;

    /**
     * 上传文件路径
     */
    private String uploadPath;

    /**
     * 文件名称
     */
    private String fileName;
}
