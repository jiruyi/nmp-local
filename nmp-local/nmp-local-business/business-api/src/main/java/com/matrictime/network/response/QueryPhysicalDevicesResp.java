package com.matrictime.network.response;

import com.matrictime.network.modelVo.PhysicalDeviceHeartbeatVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
public class QueryPhysicalDevicesResp implements Serializable {

    private static final long serialVersionUID = 7486286508896767478L;

    /**
     * 物理设备列表
     */
    private Set<String> ips;

    /**
     * 物理设备心跳状态
     */
    private List<PhysicalDeviceHeartbeatVo> vos;
}
