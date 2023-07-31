package com.matrictime.network.response;

import com.matrictime.network.modelVo.NmplConfigVo;
import com.matrictime.network.modelVo.NmplReportBusinessVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class QueryDataCollectResp implements Serializable {

    private static final long serialVersionUID = -7556881806741670160L;
    /**
     * 自动上报基础配置列表
     */
    private List<NmplConfigVo> configVos;

    /**
     * 上报业务配置列表
     */
    private List<NmplReportBusinessVo> reportBusinessVos;

    /**
     * 数据采集配置开关
     */
    private NmplConfigVo collectSwitch;
}
