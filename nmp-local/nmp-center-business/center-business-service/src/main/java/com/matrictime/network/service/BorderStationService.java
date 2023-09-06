package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.StationSummaryVo;

/**
 * @author by wangqiang
 * @date 2023/9/4.
 */
public interface BorderStationService {

    Result<Integer> receiveStationSummary(StationSummaryVo stationSummaryVo);

}
