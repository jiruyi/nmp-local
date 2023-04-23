package com.matrictime.network.response;

import com.matrictime.network.modelVo.PhysicalDeviceResourceVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class QueryPhysicalDeviceResourceResp implements Serializable {

    private static final long serialVersionUID = 964817762104932165L;

    /**
     * 物理设备资源情况列表
     */
    private List<PhysicalDeviceResourceVo> resourceVos;
}
