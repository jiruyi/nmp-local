package com.matrictime.network.dao.mapper.extend;

import com.matrictime.network.dao.model.NmpsDataInfo;
import com.matrictime.network.modelVo.DataInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DataInfoMapper {
    List<NmpsDataInfo> selectDataList(@Param("dataType") String dataType, @Param("networkId") String networkId, @Param("time") String time);

    int batchInsert(@Param("list") List<DataInfoVo> dataInfoVoList);
}
