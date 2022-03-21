package com.matrictime.network.response;

import com.matrictime.network.modelVo.DeviceInfoRelVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class QueryMonitorResp implements Serializable {
    private static final long serialVersionUID = 6176270327163234537L;

    private List<DeviceInfoRelVo> deviceInfoRelVos;
}
