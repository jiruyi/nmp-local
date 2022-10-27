package com.matrictime.network.modelVo;

import lombok.Data;

import java.util.Date;
@Data
public class CenterDeviceInfoVo {

    /**
     * 主键
     */
    private Long id;

    /**
     * 设备编号
     */
    private String deviceId;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 设备类型 01:密钥分发机 02:生成机 03:缓存机
     */
    private String deviceType;

    /**
     * 备用类型
     */
    private String otherType;

    /**
     * 接入网时间
     */
    private Date enterNetworkTime;

    /**
     * 设备管理员
     */
    private String deviceAdmain;

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
     * 内网Ip
     */
    private String lanIp;

    /**
     * 内网端口
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
     * 关联区域
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
     * 设备入网码
     */
    private byte[] byteNetworkId;
}