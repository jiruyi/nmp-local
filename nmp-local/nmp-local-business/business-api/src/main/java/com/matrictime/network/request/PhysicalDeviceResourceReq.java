package com.matrictime.network.request;

import com.matrictime.network.modelVo.PhysicalDeviceResourceVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PhysicalDeviceResourceReq implements Serializable {

    private static final long serialVersionUID = -8686584408670022398L;

    private List<PhysicalDeviceResourceVo> pdrList;
}
