package com.matrictime.network.dao.mapper.extend;


import com.matrictime.network.dao.model.NmplSignal;

import java.util.List;

public interface NmplSignalExtMapper {

    List<NmplSignal> selectPagesByUserId(String userId);

    List<String> selectDeviceIdsByUserId(String userId);
}
