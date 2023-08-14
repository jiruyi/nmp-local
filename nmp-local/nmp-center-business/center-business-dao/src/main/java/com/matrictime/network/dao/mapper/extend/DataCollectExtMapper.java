package com.matrictime.network.dao.mapper.extend;

import com.matrictime.network.dao.model.NmplDataCollect;
import com.matrictime.network.request.DataCollectRequest;

import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/8/10.
 */
public interface DataCollectExtMapper {

    List<NmplDataCollect> sumData(DataCollectRequest dataCollectRequest);

    List<NmplDataCollect> selectLoadData(DataCollectRequest dataCollectRequest);



}
