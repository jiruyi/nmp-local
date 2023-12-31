package com.matrictime.network.modelVo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 路由
 * @author   hexu
 * @date   2022-08-11
 */
@Data
public class RouteVo implements Serializable {

    private static final long serialVersionUID = 2325378929052936386L;
    /**
     * 主键
     */
    private Long id;

    /**
     * 接入基站id
     */
    private String accessDeviceId;

    /**
     * 边界基站id
     */
    private String boundaryDeviceId;

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
     * 1:存在 0:删除
     */
    private Boolean isExist;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccessDeviceId() {
        return accessDeviceId;
    }

    public void setAccessDeviceId(String accessDeviceId) {
        this.accessDeviceId = accessDeviceId == null ? null : accessDeviceId.trim();
    }

    public String getBoundaryDeviceId() {
        return boundaryDeviceId;
    }

    public void setBoundaryDeviceId(String boundaryDeviceId) {
        this.boundaryDeviceId = boundaryDeviceId == null ? null : boundaryDeviceId.trim();
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

    public Boolean getIsExist() {
        return isExist;
    }

    public void setIsExist(Boolean isExist) {
        this.isExist = isExist;
    }
}