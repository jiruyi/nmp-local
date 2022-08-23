package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplStationHeartInfo;
import com.matrictime.network.dao.model.NmplStationHeartInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplStationHeartInfoMapper {
    long countByExample(NmplStationHeartInfoExample example);

    int deleteByExample(NmplStationHeartInfoExample example);

    int insert(NmplStationHeartInfo record);

    int insertSelective(NmplStationHeartInfo record);

    List<NmplStationHeartInfo> selectByExample(NmplStationHeartInfoExample example);

    int updateByExampleSelective(@Param("record") NmplStationHeartInfo record, @Param("example") NmplStationHeartInfoExample example);

    int updateByExample(@Param("record") NmplStationHeartInfo record, @Param("example") NmplStationHeartInfoExample example);
}