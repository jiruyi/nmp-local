package com.matrictime.network.dao.domain;

import com.matrictime.network.dao.model.NmplCompanyInfo;
import com.matrictime.network.modelVo.CompanyInfoVo;

import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/8/18.
 */
public interface CompanyDomainService {

    int insertCompany(CompanyInfoVo companyInfoVo);

    int updateCompany(CompanyInfoVo companyInfoVo);

    List<NmplCompanyInfo> selectCompany(CompanyInfoVo companyInfoVo);

}
