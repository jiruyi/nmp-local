package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.StationManageVo;

import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/11/6.
 */
public interface StationManageService {

    Result<Integer> insertStationManage(StationManageVo stationManageVo);

    Result<Integer> deleteStationManage(StationManageVo stationManageVo);

}
