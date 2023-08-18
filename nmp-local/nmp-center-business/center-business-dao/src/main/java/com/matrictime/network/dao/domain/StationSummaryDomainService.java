package com.matrictime.network.dao.domain;

import com.matrictime.network.dao.model.NmplStationSummary;
import com.matrictime.network.request.StationSummaryRequest;

import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/8/17.
 */
public interface StationSummaryDomainService {

    int insertStationSummary(StationSummaryRequest stationSummaryRequest);

    int updateStationSummary(StationSummaryRequest stationSummaryRequest);

    List<NmplStationSummary> selectStationSummary(StationSummaryRequest stationSummaryRequest);
}
