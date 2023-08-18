package com.matrictime.network.dao.model;

import java.util.Date;
import lombok.Data;

/**
 * 
 * @author   cxk
 * @date   2023-08-16
 */
@Data
public class NmplBaseStationInfo {
    /**
     * 主键
     */
    private Long id;

    /**
     * 设备id
     */
    private String stationId;

    /**
     * 设备名称
     */
    private String stationName;

    /**
     * 设备类型 01:小区内基站 02:小区边界基站
     */
    private String stationType;

    /**
     * 接入网时间
     */
    private Date enterNetworkTime;

    /**
     * 设备管理员
     */
    private String stationAdmain;

    /**
     * 设备备注
     */
    private String remark;

    /**
     * 公网Ip
     */
    private String publicNetworkIp;

    /**
     * 公网端口
     */
    private String publicNetworkPort;

    /**
     * 局域网ip
     */
    private String lanIp;

    /**
     * 局域网port
     */
    private String lanPort;

    /**
     * 设备状态 01:静态  02:激活  03:禁用 04:下线
     */
    private String stationStatus;

    /**
     * 设备入网码
     */
    private String stationNetworkId;

    /**
     * 加密随机数
     */
    private String stationRandomSeed;

    /**
     * 关联小区
     */
    private String relationOperatorId;

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

    /**
     * 入网码前缀
     */
    private Long prefixNetworkId;

    /**
     * 入网码后缀
     */
    private Long suffixNetworkId;

    /**
     * 运行版本文件id
     */
    private Long runVersionId;

    /**
     * 运行版本号
     */
    private String runVersionNo;

    /**
     * 运行版本文件名称
     */
    private String runFileName;

    /**
     * 运行状态 1:未运行 2:运行中 3:已停止
     */
    private String runVersionStatus;

    /**
     * 运行版本操作时间
     */
    private Date runVersionOperTime;

    /**
     * 加载版本号
     */
    private String loadVersionNo;

    /**
     * 加载版本文件id
     */
    private Long loadVersionId;

    /**
     * 加载版本操作时间
     */
    private Date loadVersionOperTime;

    /**
     * 加载版本文件名称
     */
    private String loadFileName;

    /**
     * 当前用户数
     */
    private String currentConnectCount;

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

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId == null ? null : stationId.trim();
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName == null ? null : stationName.trim();
    }

    public String getStationType() {
        return stationType;
    }

    public void setStationType(String stationType) {
        this.stationType = stationType == null ? null : stationType.trim();
    }

    public Date getEnterNetworkTime() {
        return enterNetworkTime;
    }

    public void setEnterNetworkTime(Date enterNetworkTime) {
        this.enterNetworkTime = enterNetworkTime;
    }

    public String getStationAdmain() {
        return stationAdmain;
    }

    public void setStationAdmain(String stationAdmain) {
        this.stationAdmain = stationAdmain == null ? null : stationAdmain.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getPublicNetworkIp() {
        return publicNetworkIp;
    }

    public void setPublicNetworkIp(String publicNetworkIp) {
        this.publicNetworkIp = publicNetworkIp == null ? null : publicNetworkIp.trim();
    }

    public String getPublicNetworkPort() {
        return publicNetworkPort;
    }

    public void setPublicNetworkPort(String publicNetworkPort) {
        this.publicNetworkPort = publicNetworkPort == null ? null : publicNetworkPort.trim();
    }

    public String getLanIp() {
        return lanIp;
    }

    public void setLanIp(String lanIp) {
        this.lanIp = lanIp == null ? null : lanIp.trim();
    }

    public String getLanPort() {
        return lanPort;
    }

    public void setLanPort(String lanPort) {
        this.lanPort = lanPort == null ? null : lanPort.trim();
    }

    public String getStationStatus() {
        return stationStatus;
    }

    public void setStationStatus(String stationStatus) {
        this.stationStatus = stationStatus == null ? null : stationStatus.trim();
    }

    public String getStationNetworkId() {
        return stationNetworkId;
    }

    public void setStationNetworkId(String stationNetworkId) {
        this.stationNetworkId = stationNetworkId == null ? null : stationNetworkId.trim();
    }

    public String getStationRandomSeed() {
        return stationRandomSeed;
    }

    public void setStationRandomSeed(String stationRandomSeed) {
        this.stationRandomSeed = stationRandomSeed == null ? null : stationRandomSeed.trim();
    }

    public String getRelationOperatorId() {
        return relationOperatorId;
    }

    public void setRelationOperatorId(String relationOperatorId) {
        this.relationOperatorId = relationOperatorId == null ? null : relationOperatorId.trim();
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

    public Long getPrefixNetworkId() {
        return prefixNetworkId;
    }

    public void setPrefixNetworkId(Long prefixNetworkId) {
        this.prefixNetworkId = prefixNetworkId;
    }

    public Long getSuffixNetworkId() {
        return suffixNetworkId;
    }

    public void setSuffixNetworkId(Long suffixNetworkId) {
        this.suffixNetworkId = suffixNetworkId;
    }

    public Long getRunVersionId() {
        return runVersionId;
    }

    public void setRunVersionId(Long runVersionId) {
        this.runVersionId = runVersionId;
    }

    public String getRunVersionNo() {
        return runVersionNo;
    }

    public void setRunVersionNo(String runVersionNo) {
        this.runVersionNo = runVersionNo == null ? null : runVersionNo.trim();
    }

    public String getRunFileName() {
        return runFileName;
    }

    public void setRunFileName(String runFileName) {
        this.runFileName = runFileName == null ? null : runFileName.trim();
    }

    public String getRunVersionStatus() {
        return runVersionStatus;
    }

    public void setRunVersionStatus(String runVersionStatus) {
        this.runVersionStatus = runVersionStatus == null ? null : runVersionStatus.trim();
    }

    public Date getRunVersionOperTime() {
        return runVersionOperTime;
    }

    public void setRunVersionOperTime(Date runVersionOperTime) {
        this.runVersionOperTime = runVersionOperTime;
    }

    public String getLoadVersionNo() {
        return loadVersionNo;
    }

    public void setLoadVersionNo(String loadVersionNo) {
        this.loadVersionNo = loadVersionNo == null ? null : loadVersionNo.trim();
    }

    public Long getLoadVersionId() {
        return loadVersionId;
    }

    public void setLoadVersionId(Long loadVersionId) {
        this.loadVersionId = loadVersionId;
    }

    public Date getLoadVersionOperTime() {
        return loadVersionOperTime;
    }

    public void setLoadVersionOperTime(Date loadVersionOperTime) {
        this.loadVersionOperTime = loadVersionOperTime;
    }

    public String getLoadFileName() {
        return loadFileName;
    }

    public void setLoadFileName(String loadFileName) {
        this.loadFileName = loadFileName == null ? null : loadFileName.trim();
    }

    public String getCurrentConnectCount() {
        return currentConnectCount;
    }

    public void setCurrentConnectCount(String currentConnectCount) {
        this.currentConnectCount = currentConnectCount == null ? null : currentConnectCount.trim();
    }

    public byte[] getByteNetworkId() {
        return byteNetworkId;
    }

    public void setByteNetworkId(byte[] byteNetworkId) {
        this.byteNetworkId = byteNetworkId;
    }
}