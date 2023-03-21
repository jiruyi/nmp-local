package com.matrictime.network.modelVo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;

@Data
public class NmplVersionFileVo implements Serializable {
    private static final long serialVersionUID = 8583688845746896233L;


    /**
     * 主键
     */
    private Long id;

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

    /**
     * 1存在 0删除
     */
    private Boolean isDelete;

    /**
     * 上传时间
     */
    private Date uploadTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     *
     */
    private String createUser;

    /**
     *
     */
    private String updateUser;

}
