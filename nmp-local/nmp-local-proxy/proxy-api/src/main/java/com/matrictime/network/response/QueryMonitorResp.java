package com.matrictime.network.response;

import com.matrictime.network.modelVo.DeviceInfoRelVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class QueryMonitorResp implements Serializable {
    private static final long serialVersionUID = 6176270327163234537L;

    /**
     * 设备信息
     */
    private Map<Integer,List<DeviceInfoRelVo>> deviceInfoMap;

    /**
     * 当前用户数量
     */
    private Long userCount;

    /**
     * 当前总带宽
     */
    private Long totalBandwidth;
}
