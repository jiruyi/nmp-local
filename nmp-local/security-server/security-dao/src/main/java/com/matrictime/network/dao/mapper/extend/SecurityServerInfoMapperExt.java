package com.matrictime.network.dao.mapper.extend;

import com.matrictime.network.modelVo.SecurityServerInfoVo;
import com.matrictime.network.req.QueryServerReq;

import java.util.List;

public interface SecurityServerInfoMapperExt {

    List<SecurityServerInfoVo> queryServerByPage(QueryServerReq req);

}