package com.matrictime.network.dao.mapper.extend;

import org.apache.ibatis.annotations.Param;


public interface StationSummaryExtMapper {

    Long getSum(@Param("sumType") String sumType, @Param("companyNetworkId") String companyNetworkId);

}