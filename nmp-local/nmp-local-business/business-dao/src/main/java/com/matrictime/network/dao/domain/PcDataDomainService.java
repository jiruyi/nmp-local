package com.matrictime.network.dao.domain;

import com.matrictime.network.dao.model.NmplOutlineSorterInfo;
import com.matrictime.network.modelVo.NmplOutlineSorterInfoVo;
import com.matrictime.network.modelVo.NmplPcDataVo;
import com.matrictime.network.request.OutlineSorterReq;
import com.matrictime.network.request.PcDataReq;
import com.matrictime.network.response.PageInfo;

import java.util.List;

public interface PcDataDomainService {

    PageInfo<NmplPcDataVo> query(PcDataReq pcDataReq);

    Integer batchInsert(PcDataReq pcDataReq);

}
