package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplDeviceExtraInfo;
import com.matrictime.network.dao.model.NmplDeviceExtraInfoExample;
import java.util.List;

import com.matrictime.network.dao.model.extend.NmplDeviceInfoExt;
import com.matrictime.network.modelVo.NmplDeviceInfoExtVo;
import com.matrictime.network.request.DeviceExtraInfoRequest;
import org.apache.ibatis.annotations.Param;

public interface NmplDeviceExtraInfoMapper {
    long countByExample(NmplDeviceExtraInfoExample example);

    int deleteByExample(NmplDeviceExtraInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmplDeviceExtraInfo record);

    int insertSelective(NmplDeviceExtraInfo record);

    List<NmplDeviceExtraInfo> selectByExample(NmplDeviceExtraInfoExample example);

    NmplDeviceExtraInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmplDeviceExtraInfo record, @Param("example") NmplDeviceExtraInfoExample example);

    int updateByExample(@Param("record") NmplDeviceExtraInfo record, @Param("example") NmplDeviceExtraInfoExample example);

    int updateByPrimaryKeySelective(NmplDeviceExtraInfo record);

    int updateByPrimaryKey(NmplDeviceExtraInfo record);

    List<NmplDeviceInfoExtVo> selectDevices(DeviceExtraInfoRequest deviceExtraInfoRequest);

    List<NmplDeviceInfoExtVo> query(NmplDeviceExtraInfo nmplDeviceExtraInfo);
}