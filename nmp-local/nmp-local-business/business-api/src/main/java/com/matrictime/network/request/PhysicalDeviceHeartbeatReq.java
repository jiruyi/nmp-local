package com.matrictime.network.request;

import com.matrictime.network.modelVo.PhysicalDeviceHeartbeatVo;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class PhysicalDeviceHeartbeatReq implements Serializable {

    private static final long serialVersionUID = -2436007790132411633L;

    private List<PhysicalDeviceHeartbeatVo> heartbeatList;

    private Date uploadTime;
}
