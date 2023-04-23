package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplDeviceCount;
import com.matrictime.network.dao.model.NmplDeviceCountExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplDeviceCountMapper {
    long countByExample(NmplDeviceCountExample example);

    int deleteByExample(NmplDeviceCountExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmplDeviceCount record);

    int insertSelective(NmplDeviceCount record);

    List<NmplDeviceCount> selectByExampleWithBLOBs(NmplDeviceCountExample example);

    List<NmplDeviceCount> selectByExample(NmplDeviceCountExample example);

    NmplDeviceCount selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmplDeviceCount record, @Param("example") NmplDeviceCountExample example);

    int updateByExampleWithBLOBs(@Param("record") NmplDeviceCount record, @Param("example") NmplDeviceCountExample example);

    int updateByExample(@Param("record") NmplDeviceCount record, @Param("example") NmplDeviceCountExample example);

    int updateByPrimaryKeySelective(NmplDeviceCount record);

    int updateByPrimaryKeyWithBLOBs(NmplDeviceCount record);

    int updateByPrimaryKey(NmplDeviceCount record);
}