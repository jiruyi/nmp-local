package com.matrictime.network.dao.domain;

import com.matrictime.network.dao.model.NmplDeviceInfo;


public interface DeviceInfoDomainService {

    int insert(NmplDeviceInfo deviceInfo);

    int update(NmplDeviceInfo deviceInfo);

}
