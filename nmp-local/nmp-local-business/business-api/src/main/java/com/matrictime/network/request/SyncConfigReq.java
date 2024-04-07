package com.matrictime.network.request;

import com.matrictime.network.modelVo.ConfigVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SyncConfigReq implements Serializable {

    private static final long serialVersionUID = -6423188845541784864L;

    /**
     * 网元参数配置列表
     */
    private List<ConfigVo> configVos;


}
