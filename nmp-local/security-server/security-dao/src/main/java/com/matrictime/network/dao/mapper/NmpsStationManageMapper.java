package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmpsStationManage;
import com.matrictime.network.dao.model.NmpsStationManageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmpsStationManageMapper {
    long countByExample(NmpsStationManageExample example);

    int deleteByExample(NmpsStationManageExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmpsStationManage record);

    int insertSelective(NmpsStationManage record);

    List<NmpsStationManage> selectByExample(NmpsStationManageExample example);

    NmpsStationManage selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmpsStationManage record, @Param("example") NmpsStationManageExample example);

    int updateByExample(@Param("record") NmpsStationManage record, @Param("example") NmpsStationManageExample example);

    int updateByPrimaryKeySelective(NmpsStationManage record);

    int updateByPrimaryKey(NmpsStationManage record);
}