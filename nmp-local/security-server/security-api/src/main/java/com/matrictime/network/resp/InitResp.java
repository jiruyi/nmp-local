package com.matrictime.network.resp;

import com.matrictime.network.modelVo.NetworkCardVo;
import com.matrictime.network.modelVo.SecurityServerInfoVo;
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
}
