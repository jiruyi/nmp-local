package com.matrictime.network.dao.mapper.extend;

import com.matrictime.network.modelVo.BaseStationInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BaseStationInfoMapper {

    int batchInsert(@Param("list") List<BaseStationInfoVo> baseStationInfoVos);

    int batchDelete(@Param("list") List<Long> ids);

    int batchUpdate(@Param("list") List<BaseStationInfoVo> baseStationInfoVos);

}