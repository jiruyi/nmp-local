package com.matrictime.network.dao.domain;

import com.matrictime.network.modelVo.BaseStationInfoVo;
import com.matrictime.network.modelVo.OutlinePcVo;
import com.matrictime.network.request.BaseStationInfoRequest;
import com.matrictime.network.request.OutlinePcListRequest;
import com.matrictime.network.request.OutlinePcReq;

import java.util.List;

/**
 * @author by wangqiang
 * @date 2022/9/15.
 */
public interface OutlinePcDomainService {

    int updateOutlinePc(OutlinePcReq outlinePcReq);

    int batchInsertOutlinePc(OutlinePcListRequest listRequest);

    int insertOutlinePc(OutlinePcReq outlinePcReq);

    List<OutlinePcVo> selectOutlinePc(OutlinePcReq outlinePcReq);

    List<BaseStationInfoVo> selectBaseStation(BaseStationInfoRequest baseStationInfoRequest);
}
