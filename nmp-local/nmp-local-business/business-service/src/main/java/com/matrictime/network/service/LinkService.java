package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.LocalLinkDisplayVo;
import com.matrictime.network.modelVo.LocalLinkVo;
import com.matrictime.network.request.EditLinkReq;
import com.matrictime.network.request.QueryLinkReq;
import com.matrictime.network.response.PageInfo;

public interface LinkService {

    /**
     * 查询链路
     * @param req
     * @return
     */
    Result<PageInfo<LocalLinkDisplayVo>> queryLink(QueryLinkReq req);

    /**
     * 配置链路
     * @param req
     * @return
     */
    Result editLink(EditLinkReq req);
}
