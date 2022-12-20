package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmpStationConf;
import com.matrictime.network.dao.model.NmpStationConfExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmpStationConfMapper {
    long countByExample(NmpStationConfExample example);

    int deleteByExample(NmpStationConfExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmpStationConf record);

    int insertSelective(NmpStationConf record);

    List<NmpStationConf> selectByExample(NmpStationConfExample example);

    NmpStationConf selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmpStationConf record, @Param("example") NmpStationConfExample example);

    int updateByExample(@Param("record") NmpStationConf record, @Param("example") NmpStationConfExample example);

    int updateByPrimaryKeySelective(NmpStationConf record);

    int updateByPrimaryKey(NmpStationConf record);
}