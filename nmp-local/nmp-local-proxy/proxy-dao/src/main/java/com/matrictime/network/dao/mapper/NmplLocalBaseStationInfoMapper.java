package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplLocalBaseStationInfo;
import com.matrictime.network.dao.model.NmplLocalBaseStationInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplLocalBaseStationInfoMapper {
    long countByExample(NmplLocalBaseStationInfoExample example);

    int deleteByExample(NmplLocalBaseStationInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmplLocalBaseStationInfo record);

    int insertSelective(NmplLocalBaseStationInfo record);

    List<NmplLocalBaseStationInfo> selectByExample(NmplLocalBaseStationInfoExample example);

    NmplLocalBaseStationInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmplLocalBaseStationInfo record, @Param("example") NmplLocalBaseStationInfoExample example);

    int updateByExample(@Param("record") NmplLocalBaseStationInfo record, @Param("example") NmplLocalBaseStationInfoExample example);

    int updateByPrimaryKeySelective(NmplLocalBaseStationInfo record);

    int updateByPrimaryKey(NmplLocalBaseStationInfo record);
}