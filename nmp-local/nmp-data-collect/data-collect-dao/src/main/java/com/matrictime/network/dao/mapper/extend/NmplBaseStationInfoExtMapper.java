package com.matrictime.network.dao.mapper.extend;

import com.matrictime.network.dao.model.NmplBaseStationInfo;

import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/10/20.
 */
public interface NmplBaseStationInfoExtMapper {
    List<NmplBaseStationInfo> selectAllDevice(String netId);

}
