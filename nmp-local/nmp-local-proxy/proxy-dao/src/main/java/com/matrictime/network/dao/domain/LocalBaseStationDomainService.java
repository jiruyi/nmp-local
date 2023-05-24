package com.matrictime.network.dao.domain;

import com.matrictime.network.dao.model.NmplLocalBaseStation;
import com.matrictime.network.dao.model.NmplLocalDevice;
import com.matrictime.network.request.CurrentCountRequest;

import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/5/24.
 */
public interface LocalBaseStationDomainService {

    CurrentCountRequest selectLocalBaseStation();

}
