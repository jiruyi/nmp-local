package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.BaseStationInfoRequest;
import com.matrictime.network.request.BusinessRouteRequest;
import com.matrictime.network.response.BaseStationInfoResponse;
import com.matrictime.network.response.PageInfo;

/**
 * @author by wangqiang
 * @date 2022/9/28.
 */
public interface BusinessRouteService {

    Result<Integer> insert(BusinessRouteRequest businessRouteRequest);

    Result<Integer> delete(BusinessRouteRequest businessRouteRequest);

    Result<Integer> update(BusinessRouteRequest businessRouteRequest);

    Result<PageInfo> select(BusinessRouteRequest businessRouteRequest);

    Result<BaseStationInfoResponse> selectBaseStation(BaseStationInfoRequest baseStationInfoRequest);
}
