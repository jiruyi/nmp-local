package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.LocalLinkDisplayVo;
import com.matrictime.network.modelVo.LocalLinkVo;
import com.matrictime.network.request.DeviceInfoRequest;
import com.matrictime.network.request.EditLinkReq;
import com.matrictime.network.request.QueryDeviceForLinkReq;
import com.matrictime.network.request.QueryLinkReq;
import com.matrictime.network.response.DeviceResponse;
import com.matrictime.network.response.PageInfo;

public interface LinkService {

    /**
     * 查询链路设备列表
     * @param req
     * @return
     */
    Result<DeviceResponse> queryDeviceForLink(QueryDeviceForLinkReq req);

    /**
     * 查询链路（分页）
     * @param req
     * @return
     */
    Result<PageInfo<LocalLinkDisplayVo>> queryLinkPage(QueryLinkReq req);

    /**
     * 查询密钥中心分配（分页）
     * @param req
     * @return
     */
    Result<PageInfo<LocalLinkDisplayVo>> queryKeycenterLinkPage(QueryLinkReq req);

    /**
     * 查询链路（不分页）
     * @param req
     * @return
     */
    Result<LocalLinkDisplayVo> queryLink(QueryLinkReq req);

    /**
     * 查询密钥中心分配（不分页）
     * @param req
     * @return
     */
    Result<LocalLinkDisplayVo> queryKeycenterLink(QueryLinkReq req);

    /**
     * 配置链路
     * @param req
     * @return
     */
    Result editLink(EditLinkReq req);
}
