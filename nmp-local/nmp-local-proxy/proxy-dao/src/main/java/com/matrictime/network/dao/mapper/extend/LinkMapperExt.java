package com.matrictime.network.dao.mapper.extend;

import com.matrictime.network.modelVo.ProxyLinkVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LinkMapperExt {
    int batchInsert(@Param("list") List<ProxyLinkVo> proxyLinkVos);

    int batchUpdate(@Param("list") List<ProxyLinkVo> proxyLinkVos);
}