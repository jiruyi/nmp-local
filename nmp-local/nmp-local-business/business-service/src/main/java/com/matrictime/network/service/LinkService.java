package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.LinkVo;
import com.matrictime.network.request.EditLinkReq;
import com.matrictime.network.request.QueryLinkReq;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.response.QueryLinkResp;

public interface LinkService {

    /**
     * 查询链路
     * @param req
     * @return
     */
    Result<PageInfo<LinkVo>> queryLink(QueryLinkReq req);

    /**
     * 配置链路
     * @param req
     * @return
     */
    Result editLink(EditLinkReq req);
}
