package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplDeviceInfo;
import com.matrictime.network.dao.model.NmplDeviceInfoExample;
import java.util.List;

import com.matrictime.network.modelVo.DeviceInfoVo;
import com.matrictime.network.modelVo.StationVo;
import com.matrictime.network.request.DeviceInfoRequest;
import org.apache.ibatis.annotations.Param;

public interface NmplDeviceInfoMapper {
    long countByExample(NmplDeviceInfoExample example);

    int deleteByExample(NmplDeviceInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmplDeviceInfo record);

    int insertSelective(NmplDeviceInfo record);

    List<NmplDeviceInfo> selectByExample(NmplDeviceInfoExample example);

    NmplDeviceInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmplDeviceInfo record, @Param("example") NmplDeviceInfoExample example);

    int updateByExample(@Param("record") NmplDeviceInfo record, @Param("example") NmplDeviceInfoExample example);

    int updateByPrimaryKeySelective(NmplDeviceInfo record);

    int updateByPrimaryKey(NmplDeviceInfo record);

    int insertDevice(DeviceInfoRequest deviceInfoRequest);

    int deleteDevice(DeviceInfoRequest deviceInfoRequest);

    int updateDevice(DeviceInfoRequest deviceInfoRequest);

    List<DeviceInfoVo> selectDevice(DeviceInfoRequest deviceInfoRequest);

    List<DeviceInfoVo> selectDeviceForLinkRelation(DeviceInfoRequest deviceInfoRequest);

    List<DeviceInfoVo> selectActiveDevice(DeviceInfoRequest deviceInfoRequest);

    StationVo selectDeviceId(DeviceInfoRequest deviceInfoRequest);

    List<DeviceInfoVo> selectDeviceALl(DeviceInfoRequest deviceInfoRequest);

    List<DeviceInfoVo> getDevices(DeviceInfoRequest deviceInfoRequest);



























}