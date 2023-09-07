package com.matrictime.network.dao.domain;


import com.matrictime.network.dao.model.NmplBaseStationInfo;
import com.matrictime.network.dao.model.NmplDeviceInfo;

public interface DeviceDomainService {

   String getNetworkIdByType(String deviceType);

   NmplDeviceInfo getLocalDeviceInfo(String deviceType);

   NmplBaseStationInfo getStationInfoByLocalDCLink();
}
