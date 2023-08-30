package com.matrictime.network.dao.mapper.extend;

import com.matrictime.network.dao.model.NmplDataCollect;
import com.matrictime.network.modelVo.CompanyHeartbeatVo;
import com.matrictime.network.modelVo.DataCollectVo;
import com.matrictime.network.request.DataCollectRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/8/10.
 */
public interface DataCollectExtMapper {

    List<NmplDataCollect> sumData(DataCollectRequest dataCollectRequest);

    List<NmplDataCollect> selectLoadData(DataCollectRequest dataCollectRequest);

    int insertData(@Param("list") List<DataCollectVo> dataCollectVoList);

}
