package com.matrictime.network.dao.mapper.extend;

import com.matrictime.network.dao.model.NmplLink;
import com.matrictime.network.modelVo.DeviceInfoVo;
import com.matrictime.network.modelVo.LocalLinkDisplayVo;
import com.matrictime.network.request.DeviceInfoRequest;
import com.matrictime.network.request.QueryDeviceForLinkReq;
import com.matrictime.network.request.QueryLinkReq;

import java.util.List;

public interface NmplLinkExtMapper {

    List<DeviceInfoVo> QueryDeviceForLink(QueryDeviceForLinkReq req);

    List<LocalLinkDisplayVo> queryLink(QueryLinkReq req);

    List<LocalLinkDisplayVo> queryKeycenterLink(QueryLinkReq req);

    int insertSelectiveWithId(NmplLink record);
}