package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.DataCollectVo;
import com.matrictime.network.request.DataCollectRequest;
import com.matrictime.network.response.DataCollectResponse;

import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/8/16.
 */
public interface DataCollectService {

    Result<DataCollectResponse> selectDataCollect();
}
