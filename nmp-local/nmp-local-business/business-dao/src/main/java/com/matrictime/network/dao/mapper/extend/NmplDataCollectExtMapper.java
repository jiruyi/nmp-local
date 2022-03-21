package com.matrictime.network.dao.mapper.extend;

import com.matrictime.network.modelVo.DataCollectVo;
import com.matrictime.network.request.DataCollectReq;
import org.apache.ibatis.annotations.Param;

import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface NmplDataCollectExtMapper {

    List<DataCollectVo> linkQueryByCondition(DataCollectReq dataCollectReq);


    Integer batchInsert(@Param("list") List<DataCollectVo> dataCollectVos);

    List<DataCollectVo>selectTopTen(@Param("ids")List<String>ids,@Param("dataItemCode")String dataItemCode,@Param("uploadTime")String uploadTime);

    BigDecimal countLoad(@Param("deviceId") String deviceId, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);
}
