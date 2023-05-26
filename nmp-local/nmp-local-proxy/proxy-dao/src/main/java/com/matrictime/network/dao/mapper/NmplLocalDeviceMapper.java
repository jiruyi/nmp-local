package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplLocalDevice;
import com.matrictime.network.dao.model.NmplLocalDeviceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplLocalDeviceMapper {
    long countByExample(NmplLocalDeviceExample example);

    int deleteByExample(NmplLocalDeviceExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmplLocalDevice record);

    int insertSelective(NmplLocalDevice record);

    List<NmplLocalDevice> selectByExampleWithBLOBs(NmplLocalDeviceExample example);

    List<NmplLocalDevice> selectByExample(NmplLocalDeviceExample example);

    NmplLocalDevice selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmplLocalDevice record, @Param("example") NmplLocalDeviceExample example);

    int updateByExampleWithBLOBs(@Param("record") NmplLocalDevice record, @Param("example") NmplLocalDeviceExample example);

    int updateByExample(@Param("record") NmplLocalDevice record, @Param("example") NmplLocalDeviceExample example);

    int updateByPrimaryKeySelective(NmplLocalDevice record);

    int updateByPrimaryKeyWithBLOBs(NmplLocalDevice record);

    int updateByPrimaryKey(NmplLocalDevice record);
}