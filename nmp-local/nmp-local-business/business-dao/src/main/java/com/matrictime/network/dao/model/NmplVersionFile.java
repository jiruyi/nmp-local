package com.matrictime.network.dao.model;

import java.util.Date;
import lombok.Data;

/**
 * 
 * @author   hexu
 * @date   2022-03-09
 */
@Data
public class NmplVersionFile {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersionId() {
        return versionId;
    }

    public void setVersionId(Long versionId) {
        this.versionId = versionId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public Boolean getIsPush() {
        return isPush;
    }

    public void setIsPush(Boolean isPush) {
        this.isPush = isPush;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getIsStarted() {
        return isStarted;
    }

    public void setIsStarted(Boolean isStarted) {
        this.isStarted = isStarted;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }
}