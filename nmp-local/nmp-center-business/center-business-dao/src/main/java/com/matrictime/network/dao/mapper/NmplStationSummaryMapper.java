package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplStationSummary;
import com.matrictime.network.dao.model.NmplStationSummaryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplStationSummaryMapper {
    long countByExample(NmplStationSummaryExample example);

    int deleteByExample(NmplStationSummaryExample example);

    int deleteByPrimaryKey(@Param("sumType") String sumType, @Param("companyNetworkId") String companyNetworkId);

    int insert(NmplStationSummary record);

    int insertSelective(NmplStationSummary record);

    List<NmplStationSummary> selectByExample(NmplStationSummaryExample example);

    NmplStationSummary selectByPrimaryKey(@Param("sumType") String sumType, @Param("companyNetworkId") String companyNetworkId);

    int updateByExampleSelective(@Param("record") NmplStationSummary record, @Param("example") NmplStationSummaryExample example);

    int updateByExample(@Param("record") NmplStationSummary record, @Param("example") NmplStationSummaryExample example);

    int updateByPrimaryKeySelective(NmplStationSummary record);

    int updateByPrimaryKey(NmplStationSummary record);
}