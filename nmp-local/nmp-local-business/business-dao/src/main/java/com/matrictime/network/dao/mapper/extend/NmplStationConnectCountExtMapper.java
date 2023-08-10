package com.matrictime.network.dao.mapper.extend;

import com.matrictime.network.dao.model.NmplStationConnectCount;
import com.matrictime.network.request.StationConnectCountRequest;

import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/8/8.
 */
public interface NmplStationConnectCountExtMapper {
    List<NmplStationConnectCount> selectConnectCount(StationConnectCountRequest connectCountRequest);

}
