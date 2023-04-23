package com.matrictime.network.dao.mapper.extend;

import com.matrictime.network.dao.model.NmplDataCollect;
import com.matrictime.network.request.DataCollectReq;

import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/4/20.
 */
public interface NmplSystemDataCollectExtMapper {
    List<NmplDataCollect> distinctSystemData(DataCollectReq dataCollectReq);
}
