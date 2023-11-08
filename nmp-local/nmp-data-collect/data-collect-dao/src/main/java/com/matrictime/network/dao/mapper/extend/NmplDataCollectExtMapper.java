package com.matrictime.network.dao.mapper.extend;

import com.matrictime.network.dao.model.NmplDataCollect;
import com.matrictime.network.modelVo.NmplDataCollectVo;
import com.matrictime.network.request.DataCollectRequest;

import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/8/16.
 */
public interface NmplDataCollectExtMapper {
    List<NmplDataCollect> selectDataCollect(DataCollectRequest dataCollectRequest);

    NmplDataCollect selectLastData();

}
