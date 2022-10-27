package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplBaseStation;
import com.matrictime.network.dao.model.NmplBaseStationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplBaseStationMapper {
    long countByExample(NmplBaseStationExample example);

    int deleteByExample(NmplBaseStationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmplBaseStation record);

    int insertSelective(NmplBaseStation record);

    List<NmplBaseStation> selectByExampleWithBLOBs(NmplBaseStationExample example);

    List<NmplBaseStation> selectByExample(NmplBaseStationExample example);

    NmplBaseStation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmplBaseStation record, @Param("example") NmplBaseStationExample example);

    int updateByExampleWithBLOBs(@Param("record") NmplBaseStation record, @Param("example") NmplBaseStationExample example);

    int updateByExample(@Param("record") NmplBaseStation record, @Param("example") NmplBaseStationExample example);

    int updateByPrimaryKeySelective(NmplBaseStation record);

    int updateByPrimaryKeyWithBLOBs(NmplBaseStation record);

    int updateByPrimaryKey(NmplBaseStation record);
}