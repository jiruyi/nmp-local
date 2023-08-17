package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplDeviceInfo;
import com.matrictime.network.dao.model.NmplDeviceInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplDeviceInfoMapper {
    long countByExample(NmplDeviceInfoExample example);

    int deleteByExample(NmplDeviceInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmplDeviceInfo record);

    int insertSelective(NmplDeviceInfo record);

    List<NmplDeviceInfo> selectByExampleWithBLOBs(NmplDeviceInfoExample example);

    List<NmplDeviceInfo> selectByExample(NmplDeviceInfoExample example);

    NmplDeviceInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmplDeviceInfo record, @Param("example") NmplDeviceInfoExample example);

    int updateByExampleWithBLOBs(@Param("record") NmplDeviceInfo record, @Param("example") NmplDeviceInfoExample example);

    int updateByExample(@Param("record") NmplDeviceInfo record, @Param("example") NmplDeviceInfoExample example);

    int updateByPrimaryKeySelective(NmplDeviceInfo record);

    int updateByPrimaryKeyWithBLOBs(NmplDeviceInfo record);

    int updateByPrimaryKey(NmplDeviceInfo record);
}