package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplDeviceReportBusiness;
import com.matrictime.network.dao.model.NmplDeviceReportBusinessExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplDeviceReportBusinessMapper {
    long countByExample(NmplDeviceReportBusinessExample example);

    int deleteByExample(NmplDeviceReportBusinessExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmplDeviceReportBusiness record);

    int insertSelective(NmplDeviceReportBusiness record);

    List<NmplDeviceReportBusiness> selectByExample(NmplDeviceReportBusinessExample example);

    NmplDeviceReportBusiness selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmplDeviceReportBusiness record, @Param("example") NmplDeviceReportBusinessExample example);

    int updateByExample(@Param("record") NmplDeviceReportBusiness record, @Param("example") NmplDeviceReportBusinessExample example);

    int updateByPrimaryKeySelective(NmplDeviceReportBusiness record);

    int updateByPrimaryKey(NmplDeviceReportBusiness record);
}