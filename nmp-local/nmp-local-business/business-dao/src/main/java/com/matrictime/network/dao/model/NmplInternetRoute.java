package com.matrictime.network.dao.model;

import java.util.Date;
import lombok.Data;

/**
 * 出网路由
 * @author   hexu
 * @date   2023-09-22
 */
@Data
public class NmplInternetRoute {
    /**
     * 主键
     */
    private Long id;

    /**
     * 路由Id
     */
    private String routeId;

    /**
     * 设备入网码
     */
    private String networkId;

    /**
     * 边界基站ip_v4
     */
    private String boundaryStationIp;

    /**
     * 创建者
     */
    private String createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新者
     */
    private String updateUser;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 1:存在 0:删除
     */
    private Boolean isExist;

    /**
     * 边界基站ip_v6
     */
    private String ipV6;

    /**
     * 下一条入网码
     */
    private String nextNetworkId;

    /**
     * 跳数
     */
    private String hopCount;

    /**
     * 设备入网码
     */
    private byte[] byteNetworkId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId == null ? null : routeId.trim();
    }

    public String getNetworkId() {
        return networkId;
    }

    public void setNetworkId(String networkId) {
        this.networkId = networkId == null ? null : networkId.trim();
    }

    public String getBoundaryStationIp() {
        return boundaryStationIp;
    }

    public void setBoundaryStationIp(String boundaryStationIp) {
        this.boundaryStationIp = boundaryStationIp == null ? null : boundaryStationIp.trim();
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

    public String getIpV6() {
        return ipV6;
    }

    public void setIpV6(String ipV6) {
        this.ipV6 = ipV6 == null ? null : ipV6.trim();
    }

    public String getNextNetworkId() {
        return nextNetworkId;
    }

    public void setNextNetworkId(String nextNetworkId) {
        this.nextNetworkId = nextNetworkId == null ? null : nextNetworkId.trim();
    }

    public String getHopCount() {
        return hopCount;
    }

    public void setHopCount(String hopCount) {
        this.hopCount = hopCount == null ? null : hopCount.trim();
    }

    public byte[] getByteNetworkId() {
        return byteNetworkId;
    }

    public void setByteNetworkId(byte[] byteNetworkId) {
        this.byteNetworkId = byteNetworkId;
    }
}