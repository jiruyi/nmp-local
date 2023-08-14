package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.QueryCompanyUserReq;
import com.matrictime.network.request.QueryDeviceReq;
import com.matrictime.network.request.QueryUserReq;
import com.matrictime.network.response.QueryCompanyUserResp;
import com.matrictime.network.response.QueryDeviceResp;
import com.matrictime.network.response.queryUserResp;

public interface MonitorDisplayService {

    /**
     * 查询小区用户数
     * @param req
     * @return
     */
    Result<QueryCompanyUserResp> queryCompanyUser(QueryCompanyUserReq req);

    /**
     * 查询用户数
     * @param req
     * @return
     */
    Result<queryUserResp> queryUser(QueryUserReq req);

    /**
     * 查询设备数
     * @param req
     * @return
     */
    Result<QueryDeviceResp> queryDevice(QueryDeviceReq req);
}
