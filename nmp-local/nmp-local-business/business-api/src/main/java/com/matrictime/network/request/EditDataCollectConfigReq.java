package com.matrictime.network.request;

import com.matrictime.network.modelVo.NmplConfigVo;
import com.matrictime.network.modelVo.NmplReportBusinessVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class EditDataCollectConfigReq implements Serializable {

    private static final long serialVersionUID = 3841680074982507637L;

    /**
     * 数据采集上报业务
     */
    private List<NmplReportBusinessVo> nmplReportBusinessVos;

    /**
     * 数据采集配置
     */
    private List<NmplConfigVo> nmplConfigVos;

    private String deviceId;

    private String ip;
}
