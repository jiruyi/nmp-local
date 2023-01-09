package com.matrictime.network.dao.mapper.extend;

import com.matrictime.network.dao.model.NmpKeyInfo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface KeyInfoMapper {

    List<NmpKeyInfo> selectDataList(@Param("dataType") String record, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);
    BigDecimal getDataValueSum(@Param("dataType") String record);
}