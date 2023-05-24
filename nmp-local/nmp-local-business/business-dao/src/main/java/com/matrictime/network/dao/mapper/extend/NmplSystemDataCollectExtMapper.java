package com.matrictime.network.dao.mapper.extend;


import com.matrictime.network.modelVo.DataCollectVo;
import com.matrictime.network.modelVo.StationVo;
import com.matrictime.network.request.DataCollectReq;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author by wangqiang
 * @date 2023/4/20.
 */
public interface NmplSystemDataCollectExtMapper {
    List<StationVo> distinctSystemData(DataCollectReq dataCollectReq);

    List<DataCollectVo> distinctSystemDeviceData(DataCollectReq dataCollectReq);

    List<DataCollectVo> selectDataItemValue(@Param("dataMap")Map dataMap);
}
