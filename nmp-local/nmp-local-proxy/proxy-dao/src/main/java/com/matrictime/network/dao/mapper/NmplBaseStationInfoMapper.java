package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplBaseStationInfo;
import com.matrictime.network.dao.model.NmplBaseStationInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplBaseStationInfoMapper {
    long countByExample(NmplBaseStationInfoExample example);

    int deleteByExample(NmplBaseStationInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmplBaseStationInfo record);

    int insertSelective(NmplBaseStationInfo record);

    List<NmplBaseStationInfo> selectByExample(NmplBaseStationInfoExample example);

    NmplBaseStationInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmplBaseStationInfo record, @Param("example") NmplBaseStationInfoExample example);

    int updateByExample(@Param("record") NmplBaseStationInfo record, @Param("example") NmplBaseStationInfoExample example);

    int updateByPrimaryKeySelective(NmplBaseStationInfo record);

    int updateByPrimaryKey(NmplBaseStationInfo record);
}