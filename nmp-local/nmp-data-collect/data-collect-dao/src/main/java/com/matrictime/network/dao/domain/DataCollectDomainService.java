package com.matrictime.network.dao.domain;

import com.matrictime.network.modelVo.DataCollectVo;
import com.matrictime.network.modelVo.NmplDataCollectVo;
import com.matrictime.network.request.DataCollectRequest;

import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/8/16.
 */
public interface DataCollectDomainService {

    List<DataCollectVo> selectDataCollect();
}
