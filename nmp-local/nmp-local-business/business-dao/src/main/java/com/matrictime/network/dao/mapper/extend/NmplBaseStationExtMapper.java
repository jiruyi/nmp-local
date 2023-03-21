package com.matrictime.network.dao.mapper.extend;

import com.matrictime.network.modelVo.NmplBillVo;
import com.matrictime.network.modelVo.VersionInfoVo;
import com.matrictime.network.request.BillRequest;
import com.matrictime.network.request.VersionReq;

import java.util.List;

public interface NmplBaseStationExtMapper {

    List<VersionInfoVo> queryLoadDataByCondition(VersionReq req);

    List<VersionInfoVo> queryRunDataByCondition(VersionReq req);

}
