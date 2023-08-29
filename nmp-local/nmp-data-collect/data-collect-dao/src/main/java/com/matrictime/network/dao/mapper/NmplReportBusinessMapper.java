package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplReportBusiness;
import com.matrictime.network.dao.model.NmplReportBusinessExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplReportBusinessMapper {
    long countByExample(NmplReportBusinessExample example);

    int deleteByExample(NmplReportBusinessExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmplReportBusiness record);

    int insertSelective(NmplReportBusiness record);

    List<NmplReportBusiness> selectByExample(NmplReportBusinessExample example);

    NmplReportBusiness selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmplReportBusiness record, @Param("example") NmplReportBusinessExample example);

    int updateByExample(@Param("record") NmplReportBusiness record, @Param("example") NmplReportBusinessExample example);

    int updateByPrimaryKeySelective(NmplReportBusiness record);

    int updateByPrimaryKey(NmplReportBusiness record);
}