package com.matrictime.network.dao.mapper.extend;

import com.matrictime.network.dao.model.NmplBaseStationInfo;
import com.matrictime.network.dao.model.NmplDeviceInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NmplDeviceExtMapper {

    List<NmplDeviceInfo> selectDeviceListByMainDeviceId(@Param("deviceId") String deviceId, @Param("operatorId") String operatorId);

    List<NmplDeviceInfo> selectBaseStationListByMainDeviceId(@Param("deviceId") String deviceId, @Param("operatorId") String operatorId);

}
