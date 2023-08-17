package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.DataCollectVo;
import com.matrictime.network.modelVo.DataTimeVo;
import com.matrictime.network.request.DataCollectRequest;
import com.matrictime.network.response.DataCollectResponse;

import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/8/10.
 */
public interface DataCollectService {

    Result<List<DataTimeVo>> selectLoadData(DataCollectRequest dataCollectRequest);

    Result<Double> sumDataValue(DataCollectRequest dataCollectRequest);

    Result<Integer> insertDataCollect(DataCollectResponse dataCollectResponse);

    Result<String> selectCompanyData(DataCollectRequest dataCollectRequest);

    Result<List<DataTimeVo>> selectCompanyLoadData(DataCollectRequest dataCollectRequest);

}
