package com.matrictime.network.request;

import lombok.Data;

import java.util.List;

@Data
public class VersionReq extends BaseRequest{
    /**
     * 设备id列表
     */
    private List<String> deviceIds;
    /**
     * 设备id
     */
    private String stationNetworkId;
    /**
     * 设备名称
     */
    private String deviceName;
    /**
     * 设备类型
     */
    private String deviceType;
    /**
     * 是否全部 加载、启动、停止
     */
    private Boolean total = false;
    /**
     * 设备运行状态
     */
    private String runVersionStatus;
    /**
     * 版本文件id
     */
    private String versionId;

    /**
     * 1:存在 0:删除
     */
    private Boolean isExist=true;
    /**
     * 加载版本号
     */
    private String loadVersionNo;

}
