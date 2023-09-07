package com.matrictime.network.modelVo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class LinkVo implements Serializable {

    private static final long serialVersionUID = 2160601734224471697L;
    /**
     * 主键
     */
    private Long id;

    /**
     * 链路名称
     */
    private String linkName;

    /**
     * 链路类型: 1:单向,2:双向
     */
    private Short linkType;

    /**
     * 链路关系: 01:边界基站-边界基站,02:接入基站-密钥中心,03:边界基站-密钥中心,04:采集系统-边界基站
     */
    private String linkRelation;

    /**
     * 主设备id
     */
    private String mainDeviceId;

    /**
     * 从设备id
     */
    private String followDeviceId;

    /**
     * 从设备入网码
     */
    private String followNetworkId;

    /**
     * 从设备ip
     */
    private String followIp;

    /**
     * 从设备端口
     */
    private String followPort;

    /**
     * 主动发起认证 1:开启 0:关闭
     */
    private Boolean activeAuth;

    /**
     * 是否启用 1:启动 0:禁止
     */
    private Boolean isOn;

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
     * 主设备信息
     */
    private LinkDevice mainDeviceInfo;

    /**
     * 从设备信息
     */
    private LinkDevice followDeviceInfo;

    /**
     * 通知设备类型（01：接入基站 02：边界基站 11：秘钥中心）
     */
    private String noticeDeviceType;

    /**
     * 从设备名称
     */
    private String followDeviceName;
}
