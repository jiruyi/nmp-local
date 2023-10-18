package com.matrictime.network.dao.domain;

import com.matrictime.network.modelVo.StationSummaryVo;

import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/8/17.
 */
public interface StationSummaryDomainService {

    List<StationSummaryVo> selectSystemHeart();

    List<StationSummaryVo> selectStation();

    List<StationSummaryVo> selectDevice();

    List<StationSummaryVo> selectBorderStation();

    int insertDataPushRecord(Long maxId,String businessDataEnum);

}
