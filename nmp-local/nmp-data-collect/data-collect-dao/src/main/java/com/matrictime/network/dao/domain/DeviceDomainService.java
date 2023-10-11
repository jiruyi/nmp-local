package com.matrictime.network.dao.domain;


import com.matrictime.network.dao.model.NmplBusinessRoute;
import com.matrictime.network.dao.model.NmplDeviceInfo;
import com.matrictime.network.dao.model.NmplInternetRoute;
import com.matrictime.network.dao.model.NmplLink;

public interface DeviceDomainService {

   String getNetworkIdByType(String deviceType);

   NmplDeviceInfo getLocalDeviceInfo(String deviceType);

   NmplLink getDataCollectLink(String followNetId);

   NmplBusinessRoute getBusinessRoute();

   NmplInternetRoute getInternetRoute(String targetNetId);
}
