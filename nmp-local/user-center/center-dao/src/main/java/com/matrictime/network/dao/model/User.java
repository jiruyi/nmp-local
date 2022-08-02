package com.matrictime.network.dao.model;

import lombok.Data;

import java.util.Date;

/**
 * 用户信息表
 * @author   hexu
 * @date   2022-05-10
 */
@Data
public class User {
    /**
     * ID
     */
    private Long id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 绑定本地用户id
     */
    private String lId;

    /**
     * sid
     */
    private String sid;

    /**
     * 一体机设备ID
     */
    private String deviceId;

    /**
     * 登录ip
     */
    private String deviceIp;

    /**
     * 登录账号
     */
    private String loginAccount;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 性别（1：男 0：女）
     */
    private String sex;

    /**
     * 用户类型（00系统用户 01注册用户）
     */
    private String userType;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phoneNumber;

    /**
     * 证件类型
     */
    private String idType;

    /**
     * 证件号
     */
    private String idNo;

    /**
     * 密码
     */
    private String password;

    /**
     * 当前登录状态(0:未登录 1:已登录)
     */
    private String loginStatus;

    /**
     * 当前登录系统
     */
    private String loginAppCode;

    /**
     * 当前退出系统
     */
    private String logoutAppCode;

    /**
     * 添加好友条件（0：直接添加 1：需要询问）
     */
    private Boolean agreeFriend;

    /**
     * 删除好友是否通知（0：不通知 1：通知）
     */
    private Boolean deleteFriend;

    /**
     * 帐号状态（1正常 0停用注销）
     */
    private Boolean status;

    /**
     * 删除标志（1代表存在 0代表删除）
     */
    private Boolean isExist;

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
     * 备注
     */
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getlId() {
        return lId;
    }

    public void setlId(String lId) {
        this.lId = lId == null ? null : lId.trim();
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid == null ? null : sid.trim();
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId == null ? null : deviceId.trim();
    }

    public String getDeviceIp() {
        return deviceIp;
    }

    public void setDeviceIp(String deviceIp) {
        this.deviceIp = deviceIp == null ? null : deviceIp.trim();
    }

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount == null ? null : loginAccount.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType == null ? null : userType.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber == null ? null : phoneNumber.trim();
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType == null ? null : idType.trim();
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo == null ? null : idNo.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(String loginStatus) {
        this.loginStatus = loginStatus == null ? null : loginStatus.trim();
    }

    public String getLoginAppCode() {
        return loginAppCode;
    }

    public void setLoginAppCode(String loginAppCode) {
        this.loginAppCode = loginAppCode == null ? null : loginAppCode.trim();
    }

    public String getLogoutAppCode() {
        return logoutAppCode;
    }

    public void setLogoutAppCode(String logoutAppCode) {
        this.logoutAppCode = logoutAppCode == null ? null : logoutAppCode.trim();
    }

    public Boolean getAgreeFriend() {
        return agreeFriend;
    }

    public void setAgreeFriend(Boolean agreeFriend) {
        this.agreeFriend = agreeFriend;
    }

    public Boolean getDeleteFriend() {
        return deleteFriend;
    }

    public void setDeleteFriend(Boolean deleteFriend) {
        this.deleteFriend = deleteFriend;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getIsExist() {
        return isExist;
    }

    public void setIsExist(Boolean isExist) {
        this.isExist = isExist;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}