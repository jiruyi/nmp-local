package com.matrictime.network.modelVo;

import lombok.Data;

import java.util.Date;
@Data
public class NmplOutlineSorterInfoVo {
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
     * 设备入网码
     */
    private String stationNetworkId;

    /**
     * 设备备注
     */
    private String remark;

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
}
