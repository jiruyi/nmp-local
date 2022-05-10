package com.matrictime.network.dao.domain;

import com.matrictime.network.dao.model.NmplOutlinePcInfo;
import com.matrictime.network.dao.model.NmplOutlineSorterInfo;
import com.matrictime.network.modelVo.NmplCompanyInfoVo;
import com.matrictime.network.modelVo.NmplOutlineSorterInfoVo;
import com.matrictime.network.request.OutlinePcReq;
import com.matrictime.network.request.OutlineSorterReq;
import com.matrictime.network.response.PageInfo;

import java.util.List;

public interface OutlineSorterDomainService {
    Integer save(OutlineSorterReq outlineSorterReq);

    Integer delete(OutlineSorterReq outlineSorterReq);

    PageInfo<NmplOutlineSorterInfoVo> query(OutlineSorterReq outlineSorterReq);

    Integer modify(OutlineSorterReq outlineSorterReq);

    Integer batchInsert(List<NmplOutlineSorterInfo> nmplOutlineSorterInfoList);

    NmplOutlineSorterInfo auth(OutlineSorterReq outlineSorterReq);
}
