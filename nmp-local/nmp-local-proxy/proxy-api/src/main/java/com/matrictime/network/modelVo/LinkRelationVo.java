package com.matrictime.network.modelVo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 链路
 * @author   hexu
 * @date   2022-08-11
 */
@Data
public class LinkRelationVo implements Serializable {

    private static final long serialVersionUID = -3440337554676964970L;
    /**
     * 主键
     */
    private Long id;

    /**
     * 链路名称
     */
    private String linkName;

    /**
     * 链路类型: 01:边界基站-边界基站,02:基站-分发机,03:基站-缓存机,04:分发机-生成机
     */
    private String linkType;

    /**
     * 主设备id
     */
    private String mainDeviceId;

    /**
     * 从设备id
     */
    private String followDeviceId;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 1:存在  0删除
     */
    private String isExist;

    /**
     * 通知设备类型（00：基站 11：秘钥中心 12：生成机 13：缓存机）
     */
    private String noticeDeviceType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName == null ? null : linkName.trim();
    }

    public String getLinkType() {
        return linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType == null ? null : linkType.trim();
    }

    public String getMainDeviceId() {
        return mainDeviceId;
    }

    public void setMainDeviceId(String mainDeviceId) {
        this.mainDeviceId = mainDeviceId == null ? null : mainDeviceId.trim();
    }

    public String getFollowDeviceId() {
        return followDeviceId;
    }

    public void setFollowDeviceId(String followDeviceId) {
        this.followDeviceId = followDeviceId == null ? null : followDeviceId.trim();
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getIsExist() {
        return isExist;
    }

    public void setIsExist(String isExist) {
        this.isExist = isExist == null ? null : isExist.trim();
    }

    public String getNoticeDeviceType() {
        return noticeDeviceType;
    }

    public void setNoticeDeviceType(String noticeDeviceType) {
        this.noticeDeviceType = noticeDeviceType == null ? null : noticeDeviceType.trim();;
    }
}