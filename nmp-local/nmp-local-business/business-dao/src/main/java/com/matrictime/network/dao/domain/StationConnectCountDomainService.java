package com.matrictime.network.dao.domain;

import com.matrictime.network.request.StationConnectCountRequest;
import com.matrictime.network.response.StationConnectCountResponse;

import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/8/8.
 */
public interface StationConnectCountDomainService {

    int selectConnectCount(StationConnectCountRequest stationConnectCountRequest);

    int insertConnectCount(StationConnectCountResponse stationConnectCountResponse);

}
