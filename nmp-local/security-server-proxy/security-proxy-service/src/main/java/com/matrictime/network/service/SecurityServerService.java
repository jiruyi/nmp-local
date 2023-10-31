package com.matrictime.network.service;

import com.matrictime.network.modelVo.SecurityServerProxyVo;

import java.util.List;

public interface SecurityServerService {

    /**
     * 获取当前代理上配置的安全服务器列表
     * @return
     */
    List<SecurityServerProxyVo> getLocalServerVos();
}
