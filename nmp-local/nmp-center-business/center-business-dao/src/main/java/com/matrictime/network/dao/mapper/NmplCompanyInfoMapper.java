package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplCompanyInfo;
import com.matrictime.network.dao.model.NmplCompanyInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NmplCompanyInfoMapper {
    long countByExample(NmplCompanyInfoExample example);

    int deleteByExample(NmplCompanyInfoExample example);

    int deleteByPrimaryKey(String companyNetworkId);

    int insert(NmplCompanyInfo record);

    int insertSelective(NmplCompanyInfo record);

    List<NmplCompanyInfo> selectByExample(NmplCompanyInfoExample example);

    NmplCompanyInfo selectByPrimaryKey(String companyNetworkId);

    int updateByExampleSelective(@Param("record") NmplCompanyInfo record, @Param("example") NmplCompanyInfoExample example);

    int updateByExample(@Param("record") NmplCompanyInfo record, @Param("example") NmplCompanyInfoExample example);

    int updateByPrimaryKeySelective(NmplCompanyInfo record);

    int updateByPrimaryKey(NmplCompanyInfo record);
}