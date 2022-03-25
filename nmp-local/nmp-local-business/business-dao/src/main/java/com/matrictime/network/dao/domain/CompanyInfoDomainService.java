package com.matrictime.network.dao.domain;


import com.matrictime.network.modelVo.NmplCompanyInfoVo;
import com.matrictime.network.request.CompanyInfoRequest;
import com.matrictime.network.response.PageInfo;

import java.util.List;

public interface CompanyInfoDomainService {
    public Integer save(CompanyInfoRequest companyInfoRequest);

    public Integer delete(CompanyInfoRequest companyInfoRequest);

    public Integer modify(CompanyInfoRequest companyInfoRequest);

    public PageInfo<NmplCompanyInfoVo> queryByConditions(CompanyInfoRequest companyInfoRequest);
//
//    public RoleResponse queryOne(CompanyInfoRequest roleRequest);

    public String getPreBID(String companyCode);

    public List<NmplCompanyInfoVo> queryCompanyList(CompanyInfoRequest companyInfoRequest);
}
