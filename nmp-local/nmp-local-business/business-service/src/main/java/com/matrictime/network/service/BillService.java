package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.BillRequest;
import com.matrictime.network.request.CompanyInfoRequest;
import com.matrictime.network.request.RoleRequest;

import com.matrictime.network.response.PageInfo;

import java.util.concurrent.Future;

public interface BillService {


    Result<PageInfo> queryByConditon(BillRequest billRequest);


    Future<Result> save(BillRequest billRequest);

}
