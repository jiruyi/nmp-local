package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmpHeartReport;
import com.matrictime.network.dao.model.NmpHeartReportExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmpHeartReportMapper {
    long countByExample(NmpHeartReportExample example);

    int deleteByExample(NmpHeartReportExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmpHeartReport record);

    int insertSelective(NmpHeartReport record);

    List<NmpHeartReport> selectByExample(NmpHeartReportExample example);

    NmpHeartReport selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmpHeartReport record, @Param("example") NmpHeartReportExample example);

    int updateByExample(@Param("record") NmpHeartReport record, @Param("example") NmpHeartReportExample example);

    int updateByPrimaryKeySelective(NmpHeartReport record);

    int updateByPrimaryKey(NmpHeartReport record);
}