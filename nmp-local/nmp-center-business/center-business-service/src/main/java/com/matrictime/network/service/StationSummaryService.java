package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.StationSummaryVo;

/**
 * @author by wangqiang
 * @date 2023/8/17.
 */
public interface StationSummaryService {

    Result<Integer> receiveStationSummary(StationSummaryVo stationSummaryVo);

}
