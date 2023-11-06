package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.req.DnsManageRequest;
import com.matrictime.network.req.StationManageRequest;
import com.matrictime.network.resp.DnsManageResp;
import com.matrictime.network.resp.StationManageResp;

/**
 * @author by wangqiang
 * @date 2023/11/3.
 */
public interface StationManageService {

    Result<Integer> insertStationManage(StationManageRequest stationManageRequest);

    Result<Integer> deleteStationManage(StationManageRequest stationManageRequest);

    Result<StationManageResp> selectStationManage(StationManageRequest stationManageRequest);

    Result<Integer> updateStationManage(StationManageRequest stationManageRequest);

}
