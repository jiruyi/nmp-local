package com.matrictime.network.response;

import com.matrictime.network.modelVo.CompanyHeartbeatVo;
import com.matrictime.network.modelVo.CompanyInfoVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class QueryMapInfoResp implements Serializable {

    private static final long serialVersionUID = -325644178398039096L;

    /**
     * 小区信息
     */
    private List<CompanyInfoVo> companyInfoVos;

    /**
     * 小区心跳关联信息
     */
    private List<CompanyHeartbeatVo> companyHeartbeatVos;
}
