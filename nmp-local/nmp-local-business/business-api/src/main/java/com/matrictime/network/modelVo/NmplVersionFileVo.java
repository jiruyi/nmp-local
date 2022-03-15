package com.matrictime.network.modelVo;

import lombok.Data;

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
     * 版本主键
     */
    private Long versionId;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 1 已推送 0未推送
     */
    private Boolean isPush;

    /**
     * 1 正常 0失效
     */
    private Boolean status;

    /**
     * 1 启动 0未启动
     */
    private Boolean isStarted;

    /**
     * 描述
     */
    private String remark;

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
