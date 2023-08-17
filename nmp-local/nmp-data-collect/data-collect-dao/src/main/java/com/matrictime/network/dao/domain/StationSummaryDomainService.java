package com.matrictime.network.dao.domain;

import com.matrictime.network.modelVo.StationSummaryVo;

import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/8/17.
 */
public interface StationSummaryDomainService {

    StationSummaryVo selectSystemHeart();

    StationSummaryVo selectStation();

    StationSummaryVo selectDevice();

    StationSummaryVo selectBorderStation();

}
