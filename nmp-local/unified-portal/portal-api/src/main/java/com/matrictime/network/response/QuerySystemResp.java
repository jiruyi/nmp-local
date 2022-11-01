package com.matrictime.network.response;

import com.matrictime.network.modelVo.PortalSystemVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class QuerySystemResp implements Serializable {

    private static final long serialVersionUID = 4637349934476354112L;

    /**
     * 系统信息列表
     */
    List<PortalSystemVo> list;
}
