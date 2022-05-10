package com.matrictime.network.dao.mapper.extend;

import com.matrictime.network.dao.model.NmplOutlinePcInfo;
import com.matrictime.network.modelVo.NmplBillVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NmplOutlinePcInfoExtMapper {

    Integer batchInsert(@Param("list") List<NmplOutlinePcInfo> nmplOutlinePcInfos);
}
