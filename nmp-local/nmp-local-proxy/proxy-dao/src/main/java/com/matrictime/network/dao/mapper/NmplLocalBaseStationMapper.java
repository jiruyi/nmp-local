package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplLocalBaseStation;
import com.matrictime.network.dao.model.NmplLocalBaseStationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplLocalBaseStationMapper {
    long countByExample(NmplLocalBaseStationExample example);

    int deleteByExample(NmplLocalBaseStationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmplLocalBaseStation record);

    int insertSelective(NmplLocalBaseStation record);

    List<NmplLocalBaseStation> selectByExampleWithBLOBs(NmplLocalBaseStationExample example);

    List<NmplLocalBaseStation> selectByExample(NmplLocalBaseStationExample example);

    NmplLocalBaseStation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmplLocalBaseStation record, @Param("example") NmplLocalBaseStationExample example);

    int updateByExampleWithBLOBs(@Param("record") NmplLocalBaseStation record, @Param("example") NmplLocalBaseStationExample example);

    int updateByExample(@Param("record") NmplLocalBaseStation record, @Param("example") NmplLocalBaseStationExample example);

    int updateByPrimaryKeySelective(NmplLocalBaseStation record);

    int updateByPrimaryKeyWithBLOBs(NmplLocalBaseStation record);

    int updateByPrimaryKey(NmplLocalBaseStation record);
}