package com.matrictime.network.modelVo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class DeviceInfoRelVo implements Serializable {

    private static final long serialVersionUID = 4362179654830632078L;
    /**
     * 主键
     */
    private Long id;

    /**
     * 设备id
     */
    private String deviceId;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 设备类型 1:基站 2 分发机 3 生成机 4 缓存机
     */
    private String deviceType;

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
     * 同级设备
     */
    private List<DeviceInfoRelVo> relDevices;

    /**
     * 子集设备
     */
    private List<DeviceInfoRelVo> childrenDevices;

}
