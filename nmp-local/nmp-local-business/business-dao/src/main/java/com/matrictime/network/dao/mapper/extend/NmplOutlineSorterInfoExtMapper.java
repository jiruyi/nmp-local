package com.matrictime.network.dao.mapper.extend;

import com.matrictime.network.dao.model.NmplOutlinePcInfo;
import com.matrictime.network.dao.model.NmplOutlineSorterInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NmplOutlineSorterInfoExtMapper {
    Integer batchInsert(@Param("list") List<NmplOutlineSorterInfo> nmplOutlineSorterInfoList);
}
