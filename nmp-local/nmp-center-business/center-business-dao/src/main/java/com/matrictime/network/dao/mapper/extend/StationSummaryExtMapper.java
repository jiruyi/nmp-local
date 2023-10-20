package com.matrictime.network.dao.mapper.extend;

import com.matrictime.network.dao.model.NmplCompanyHeartbeat;
import com.matrictime.network.dao.model.NmplCompanyHeartbeatExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface StationSummaryExtMapper {

    Long getSum(@Param("sumType") String sumType, @Param("companyNetworkId") String companyNetworkId);

    Long getSumByIn(@Param("sumType") String sumType, @Param("companyNetworkIds") List<String> companyNetworkIds);

    List<NmplCompanyHeartbeat> queryHeart();

}