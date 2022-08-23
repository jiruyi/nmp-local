package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplLocalDeviceInfo;
import com.matrictime.network.dao.model.NmplLocalDeviceInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplLocalDeviceInfoMapper {
    long countByExample(NmplLocalDeviceInfoExample example);

    int deleteByExample(NmplLocalDeviceInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmplLocalDeviceInfo record);

    int insertSelective(NmplLocalDeviceInfo record);

    List<NmplLocalDeviceInfo> selectByExample(NmplLocalDeviceInfoExample example);

    NmplLocalDeviceInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmplLocalDeviceInfo record, @Param("example") NmplLocalDeviceInfoExample example);

    int updateByExample(@Param("record") NmplLocalDeviceInfo record, @Param("example") NmplLocalDeviceInfoExample example);

    int updateByPrimaryKeySelective(NmplLocalDeviceInfo record);

    int updateByPrimaryKey(NmplLocalDeviceInfo record);
}