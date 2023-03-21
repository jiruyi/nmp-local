package com.matrictime.network.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
public class UploadVersionFileReq implements Serializable {
    private static final long serialVersionUID = -5300073025540384824L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 文件
     */
    private MultipartFile file;

    /**
     * QIBS:基站 QEBS:边界基站 QKC:密钥中心 QNMP:网管代理
     */
    private String systemType;

    /**
     * 版本号
     */
    private String versionNo;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件大小（mb）
     */
    private String fileSize;

    /**
     * 版本描述
     */
    private String versionDesc;

}
