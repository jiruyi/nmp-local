package com.matrictime.network.dao.mapper.extend;

import com.matrictime.network.dao.model.NmplBaseStationInfo;
import com.matrictime.network.dao.model.NmplDeviceInfo;
import com.matrictime.network.modelVo.VersionInfoVo;
import com.matrictime.network.request.VersionReq;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NmplDeviceExtMapper {

    List<NmplDeviceInfo> selectDeviceListByMainDeviceId(@Param("deviceId") String deviceId, @Param("operatorId") String operatorId);

    List<NmplDeviceInfo> selectBaseStationListByMainDeviceId(@Param("deviceId") String deviceId, @Param("operatorId") String operatorId);


    List<VersionInfoVo> queryLoadDataByCondition(VersionReq req);

    List<VersionInfoVo> queryRunDataByCondition(VersionReq req);


}
