package com.matrictime.network.dao.mapper.extend;

import com.matrictime.network.modelVo.NetworkCardVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NetworkCardMapperExt {
    int batchInsert(@Param("list") List<NetworkCardVo> proxyLinkVos);

    int batchUpdate(@Param("list") List<NetworkCardVo> proxyLinkVos);
}