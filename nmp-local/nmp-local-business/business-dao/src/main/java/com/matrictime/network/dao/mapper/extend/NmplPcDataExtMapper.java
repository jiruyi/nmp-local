package com.matrictime.network.dao.mapper.extend;

import com.matrictime.network.modelVo.NmplBillVo;
import com.matrictime.network.modelVo.NmplPcDataVo;
import com.matrictime.network.request.BillRequest;
import com.matrictime.network.request.PcDataReq;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NmplPcDataExtMapper {
    List<NmplPcDataVo> queryByCondition(PcDataReq pcDataReq);

    Integer batchInsert(@Param("list") List<NmplPcDataVo> nmplPcDataVoList);
}
