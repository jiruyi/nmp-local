package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.response.CompanyInfoResponse;

/**
 * @author by wangqiang
 * @date 2023/8/18.
 */
public interface CompanyService {

    Result<Integer> receiveCompany(CompanyInfoResponse companyInfoResponse);

}
