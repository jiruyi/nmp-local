package com.matrictime.network.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
public class UploadVersionFileReq implements Serializable {
    private static final long serialVersionUID = -5300073025540384824L;

    /**
     * 文件
     */
    private MultipartFile file;

    /**
     * 所属系统id
     */
    private String systemId;

    /**
     * 版本id
     */
    private Long versionId;
}
