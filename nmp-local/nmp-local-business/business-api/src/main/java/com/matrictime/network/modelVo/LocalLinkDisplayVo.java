package com.matrictime.network.modelVo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class LocalLinkDisplayVo implements Serializable {

    private static final long serialVersionUID = -1095357652551829576L;

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
     * 本端设备id
     */
    private String mainDeviceId;

    /**
     * 设备类型 01:小区内基站 02:小区边界基站 11:密钥中心 12:生成机 13:缓存机 20:采集设备 21:指控中心
     */
    private String mainDeviceType;

    /**
     * 对端设备id
     */
    private String followDeviceId;

    /**
     * 对端设备入网ID
     */
    private String followNetworkId;

    /**
     * 对端设备IP
     */
    private String followIp;

    /**
     * 对端设备端口
     */
    private String followPort;

    /**
     * 主动发起认证 1:开启 0:关闭
     */
    private Boolean activeAuth;

    /**
     * 链路是否启用 1:启动 0:禁止
     */
    private Boolean isOn;

    /**
     * 链路创建人
     */
    private String createUser;

    /**
     * 链路创建时间
     */
    private Date createTime;

    /**
     * 链路修改人
     */
    private String updateUser;

    /**
     * 链路修改时间
     */
    private Date updateTime;

    /**
     * 链路是否存在 1:存在 0:删除
     */
    private Boolean isExist;

    /**
     * 本端设备名称
     */
    private String mainDeviceName;

    /**
     * 对端设备名称
     */
    private String followDeviceName;

}
