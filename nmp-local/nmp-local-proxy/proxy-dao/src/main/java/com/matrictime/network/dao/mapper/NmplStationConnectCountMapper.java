package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplStationConnectCount;
import com.matrictime.network.dao.model.NmplStationConnectCountExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplStationConnectCountMapper {
    long countByExample(NmplStationConnectCountExample example);

    int deleteByExample(NmplStationConnectCountExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmplStationConnectCount record);

    int insertSelective(NmplStationConnectCount record);

    List<NmplStationConnectCount> selectByExample(NmplStationConnectCountExample example);

    NmplStationConnectCount selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmplStationConnectCount record, @Param("example") NmplStationConnectCountExample example);

    int updateByExample(@Param("record") NmplStationConnectCount record, @Param("example") NmplStationConnectCountExample example);

    int updateByPrimaryKeySelective(NmplStationConnectCount record);

    int updateByPrimaryKey(NmplStationConnectCount record);
}