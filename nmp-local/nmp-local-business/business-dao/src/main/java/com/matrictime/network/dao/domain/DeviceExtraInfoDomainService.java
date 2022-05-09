package com.matrictime.network.dao.domain;


import com.matrictime.network.dao.model.NmplDeviceExtraInfo;

import java.util.List;

public interface DeviceExtraInfoDomainService {
    int insert(NmplDeviceExtraInfo nmplDeviceExtraInfo);

    List<NmplDeviceExtraInfo> selectByCondition(NmplDeviceExtraInfo nmplDeviceExtraInfo);

    int update(NmplDeviceExtraInfo nmplDeviceExtraInfo);

    int delete(NmplDeviceExtraInfo nmplDeviceExtraInfo);


}
