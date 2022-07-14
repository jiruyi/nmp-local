package com.matrictime.network.dao.domain;


import com.matrictime.network.dao.model.NmplDeviceExtraInfo;
import com.matrictime.network.modelVo.DeviceExtraVo;
import com.matrictime.network.modelVo.NmplDeviceInfoExtVo;
import com.matrictime.network.request.DeviceExtraInfoRequest;
import com.matrictime.network.response.PageInfo;

import java.util.List;


public interface DeviceExtraInfoDomainService {
    int insert(NmplDeviceExtraInfo nmplDeviceExtraInfo);

    PageInfo<DeviceExtraVo> selectByCondition(DeviceExtraInfoRequest deviceExtraInfoRequest);

    int update(NmplDeviceExtraInfo nmplDeviceExtraInfo);

    int delete(NmplDeviceExtraInfo nmplDeviceExtraInfo);

    List<NmplDeviceInfoExtVo> selectDevices(DeviceExtraInfoRequest deviceExtraInfoRequest);

    List<NmplDeviceInfoExtVo> query(NmplDeviceExtraInfo nmplDeviceExtraInfo);
}
