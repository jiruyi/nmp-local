package com.matrictime.network.dao.mapper.extend;

import com.matrictime.network.dao.model.NmpCaConf;
import com.matrictime.network.dao.model.NmpCaConfExample;
import com.matrictime.network.dao.model.NmpKeyInfo;
import com.matrictime.network.dao.model.NmpKeyInfoExample;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface KeyInfoMapper {

    List<NmpKeyInfo> selectDataList(@Param("dataType") String record, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);
}