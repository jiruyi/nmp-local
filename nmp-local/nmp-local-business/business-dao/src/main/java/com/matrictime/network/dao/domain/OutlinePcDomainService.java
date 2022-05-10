package com.matrictime.network.dao.domain;

import com.matrictime.network.dao.model.NmplOutlinePcInfo;
import com.matrictime.network.dao.model.NmplOutlineSorterInfo;
import com.matrictime.network.modelVo.NmplOutlinePcInfoVo;
import com.matrictime.network.request.OutlinePcReq;
import com.matrictime.network.response.PageInfo;

import java.util.List;

public interface OutlinePcDomainService {
    Integer save(OutlinePcReq outlinePcReq);

    Integer delete(OutlinePcReq outlinePcReq);

    PageInfo<NmplOutlinePcInfoVo> query(OutlinePcReq outlinePcReq);

    Integer modify(OutlinePcReq outlinePcReq);

    Integer batchInsert(List<NmplOutlinePcInfo> nmplOutlinePcInfoList);
}
