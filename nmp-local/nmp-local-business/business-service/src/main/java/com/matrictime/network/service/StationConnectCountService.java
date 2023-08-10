package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.StationConnectCountRequest;
import com.matrictime.network.response.StationConnectCountResponse;

/**
 * @author by wangqiang
 * @date 2023/8/8.
 */
public interface StationConnectCountService {

    Result<Integer> selectConnectCount(StationConnectCountRequest stationConnectCountRequest);

    Result<Integer> insertConnectCount(StationConnectCountResponse stationConnectCountResponse);

}
