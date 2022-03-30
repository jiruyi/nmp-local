package com.matrictime.network.dao.mapper.extend;


import com.matrictime.network.dao.model.NmplSignal;
import com.matrictime.network.dao.model.extend.NmplDeviceInfoExt;

import java.util.List;

public interface NmplSignalExtMapper {

    List<NmplSignal> selectPagesByUserId(String userId);

    List<String> selectDeviceIdsByUserId(String userId);

    List<NmplDeviceInfoExt> selectDevices();
}
