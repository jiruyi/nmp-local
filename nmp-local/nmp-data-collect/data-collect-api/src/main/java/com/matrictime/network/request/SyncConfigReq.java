package com.matrictime.network.request;

import com.matrictime.network.modelVo.NmplConfigVo;
import com.matrictime.network.modelVo.NmplReportBusinessVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SyncConfigReq implements Serializable {

    private static final long serialVersionUID = -1925915131345361612L;

    /**
     * 批量编辑对象-修改必输
     */
    private List<NmplConfigVo> configVos;

    /**
     * 业务列表
     */
    private List<NmplReportBusinessVo> reportBusiness;
}
