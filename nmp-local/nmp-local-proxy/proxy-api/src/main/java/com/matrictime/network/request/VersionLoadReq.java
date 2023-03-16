package com.matrictime.network.request;

import com.matrictime.network.modelVo.NmplVersionVo;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;

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
     * 文件的md5值
     */
    private String md5;
}
