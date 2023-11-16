package com.matrictime.network.resp;

import com.matrictime.network.modelVo.*;
import lombok.Data;

import java.util.List;

@Data
public class InitResp {

    /**
     * 初始化安全服务器信息列表
     */
    List<SecurityServerInfoVo> initServerInfoVos;

    /**
     * 初始化安全服务器关联网卡信息列表
     */
    List<NetworkCardVo> initNetworkCardVos;

    /**
     * 初始化ca管理
     */
    List<CaManageVo> initCaManageVos;

    /**
     * 初始化基站管理
     */
    List<StationManageVo> initStationManageVos;

    /**
     * 初始化dns管理
     */
    List<DnsManageVo> initDnsManageVo;

    /**
     * 初始化配置管理
     */
    List<ServerConfigVo> initServerConfigVo;
}
