package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.SecurityServerProxyVo;
import com.matrictime.network.req.EditServerProxyReq;

import java.util.List;

public interface SecurityServerService {

    /**
     * 获取当前代理上配置的安全服务器列表
     * @return
     */
    List<SecurityServerProxyVo> getLocalServerVos();

    /**
     * 安全服务器更新
     * @param req
     * @return
     */
    Result updateServer(EditServerProxyReq req);
}
