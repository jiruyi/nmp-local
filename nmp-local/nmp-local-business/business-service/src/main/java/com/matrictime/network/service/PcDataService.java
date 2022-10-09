package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.BillRequest;
import com.matrictime.network.request.PcDataReq;
import com.matrictime.network.response.PageInfo;

import java.util.concurrent.Future;

public interface PcDataService {

    Result<PageInfo> queryByConditon(PcDataReq pcDataReq);


    Future<Result> save(PcDataReq pcDataReq);
}
