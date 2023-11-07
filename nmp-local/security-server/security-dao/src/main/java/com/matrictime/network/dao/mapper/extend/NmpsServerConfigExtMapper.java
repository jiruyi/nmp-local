package com.matrictime.network.dao.mapper.extend;

import com.matrictime.network.modelVo.ServerConfigVo;
import com.matrictime.network.req.ServerConfigRequest;

import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/11/2.
 */
public interface NmpsServerConfigExtMapper {

    List<ServerConfigVo> selectConfig(ServerConfigRequest serverConfigRequest);
}
