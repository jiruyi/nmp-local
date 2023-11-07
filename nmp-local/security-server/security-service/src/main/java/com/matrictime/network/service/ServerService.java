package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.PageInfo;
import com.matrictime.network.modelVo.SecurityServerInfoVo;
import com.matrictime.network.req.EditServerReq;
import com.matrictime.network.req.HeartReportReq;
import com.matrictime.network.req.QueryServerReq;
import com.matrictime.network.req.StartServerReq;

import java.util.List;

public interface ServerService {

    /**
     * 查询安全服务器列表(分页)
     * @return
     */
    Result<PageInfo<SecurityServerInfoVo>> queryServerByPage(QueryServerReq req);

    /**
     * 查询安全服务器列表(不分页)
     * @return
     */
    Result<List<SecurityServerInfoVo>> queryServer(QueryServerReq req);

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

    /**
     * 安全服务器状态上报
     * @param req
     * @return
     */
    Result heartReport(HeartReportReq req);


}
