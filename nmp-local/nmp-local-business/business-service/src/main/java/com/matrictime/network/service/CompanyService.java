package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.CompanyInfoRequest;
import com.matrictime.network.request.RoleRequest;
import com.matrictime.network.response.PageInfo;

public interface CompanyService {
    Result save(CompanyInfoRequest companyInfoRequest);

    Result modify(CompanyInfoRequest companyInfoRequest);

    Result delete(CompanyInfoRequest companyInfoRequest);

    Result<PageInfo>queryByConditon(CompanyInfoRequest companyInfoRequest);

}
