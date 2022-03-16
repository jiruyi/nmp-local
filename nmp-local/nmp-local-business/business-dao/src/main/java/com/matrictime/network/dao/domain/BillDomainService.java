package com.matrictime.network.dao.domain;

import com.matrictime.network.modelVo.NmplBillVo;
import com.matrictime.network.modelVo.NmplCompanyInfoVo;
import com.matrictime.network.request.BillRequest;
import com.matrictime.network.request.CompanyInfoRequest;
import com.matrictime.network.response.PageInfo;

public interface BillDomainService {
    public PageInfo<NmplBillVo> queryByConditions(BillRequest billRequest);

    public Integer save(BillRequest billRequest);
}
