package com.matrictime.network.dao.mapper.extend;


import com.matrictime.network.modelVo.StationVo;
import com.matrictime.network.request.DataCollectReq;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/4/20.
 */
public interface NmplSystemDataCollectExtMapper {
    List<StationVo> distinctSystemData(DataCollectReq dataCollectReq);

    List<StationVo> distinctSystemDeviceData(DataCollectReq dataCollectReq);

    List<StationVo> selectDataItemValue(@Param("deviceId") List<String> deviceIdList);
}
