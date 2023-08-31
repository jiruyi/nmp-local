package com.matrictime.network.dao.domain;

import com.matrictime.network.modelVo.DataCollectVo;
import com.matrictime.network.modelVo.DataTimeVo;
import com.matrictime.network.modelVo.PercentageFlowVo;
import com.matrictime.network.request.DataCollectRequest;
import com.matrictime.network.response.DataCollectResponse;

import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/8/10.
 */
public interface DataCollectDomainService {

    List<DataTimeVo> selectLoadData(DataCollectRequest dataCollectRequest);

    Double sumDataValue(DataCollectRequest dataCollectRequest);

    int insertDataCollect(List<DataCollectVo> list);

    List<PercentageFlowVo> selectCompanyData();
}
