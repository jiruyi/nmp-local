package com.matrictime.network.response;

import com.matrictime.network.modelVo.StationInfoVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class QueryVersionFileDetailResp implements Serializable {
    private static final long serialVersionUID = 2106179934326895394L;

    /**
     * 设备类型
     */
    private String systemId;

    /**
     * 版本类型
     */
    private String versionNo;

    /**
     *
     */
    private List<StationInfoVo> stationInfoVos;
}
