package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.CenterNmplOutlinePcInfoVo;
import com.matrictime.network.request.OutlinePcListRequest;
import com.matrictime.network.request.OutlinePcReq;

import java.util.List;

/**
 * @author by wangqiang
 * @date 2022/9/15.
 */
public interface OutlinePcService {
    Result<Integer> updateOutlinePc(OutlinePcReq outlinePcReq);

    Result<Integer> batchInsertOutlinePc(OutlinePcListRequest listRequest);

    void initInfo(List<CenterNmplOutlinePcInfoVo> list);
}
