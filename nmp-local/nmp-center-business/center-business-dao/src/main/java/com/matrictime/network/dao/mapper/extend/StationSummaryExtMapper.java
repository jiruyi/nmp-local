package com.matrictime.network.dao.mapper.extend;

import org.apache.ibatis.annotations.Param;


public interface StationSummaryExtMapper {

    long getSum(@Param("sumType") String sumType, @Param("companyNetworkId") String companyNetworkId);

}