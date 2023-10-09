package com.matrictime.network.dao.mapper.extend;

import com.matrictime.network.dao.model.NmplLink;
import com.matrictime.network.modelVo.LocalLinkDisplayVo;
import com.matrictime.network.request.QueryLinkReq;

import java.util.List;

public interface NmplLinkExtMapper {

    List<LocalLinkDisplayVo> queryLink(QueryLinkReq req);

    List<LocalLinkDisplayVo> queryKeycenterLink(QueryLinkReq req);

    int insertSelectiveWithId(NmplLink record);
}