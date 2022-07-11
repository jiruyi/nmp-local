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

    List<DataCollectVo> stationLinkQuery(DataCollectReq dataCollectReq);


    Integer batchInsert(@Param("list") List<DataCollectVo> dataCollectVos);

    Integer batchInsertLoad(@Param("list") List<DataCollectVo> dataCollectVos);

    List<DataCollectVo>selectTopTenDesc(@Param("ids")List<String>ids,@Param("dataItemCode")String dataItemCode,@Param("uploadTime")Date uploadTime);

    List<DataCollectVo>selectTopTenAsc(@Param("ids")List<String>ids,@Param("dataItemCode")String dataItemCode,@Param("uploadTime")Date uploadTime);

    // TODO: 2022/4/1 sql中有临时写死值需要在上前确认清
    BigDecimal countLoad(@Param("deviceId") String deviceId, @Param("dataItemCode") String dataItemCode, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);
}
