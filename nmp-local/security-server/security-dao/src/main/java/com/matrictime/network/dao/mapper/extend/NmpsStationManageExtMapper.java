package com.matrictime.network.dao.mapper.extend;

import com.matrictime.network.modelVo.StationManageVo;
import com.matrictime.network.req.StationManageRequest;

import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/11/3.
 */
public interface NmpsStationManageExtMapper {

    List<StationManageVo> selectStationManage(StationManageRequest stationManageRequest);
}
