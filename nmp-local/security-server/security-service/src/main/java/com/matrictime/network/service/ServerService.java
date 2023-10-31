package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.PageInfo;
import com.matrictime.network.modelVo.SecurityServerInfoVo;
import com.matrictime.network.req.EditServerReq;
import com.matrictime.network.req.QueryServerReq;
import com.matrictime.network.req.StartServerReq;

import java.util.List;

public interface ServerService {

    /**
     * 查询安全服务器列表
     * @return
     */
    Result<PageInfo<SecurityServerInfoVo>> queryServer(QueryServerReq req);

    /**
     * 编辑安全服务器
     * @param req
     * @return
     */
    Result editServer(EditServerReq req);

    /**
     * 启动安全服务器
     * @param req
     * @return
     */
    Result startServer(StartServerReq req);


}
