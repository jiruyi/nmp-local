package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.DataCollectReq;
import com.matrictime.network.request.TerminalDataListRequest;
import com.matrictime.network.request.TerminalDataReq;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.TerminalDataRequest;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.response.TerminalDataResponse;

/**
 * @author by wangqiang
 * @date 2023/4/19.
 */
public interface TerminalDataService {
    Result<PageInfo> selectTerminalData(TerminalDataRequest terminalDataRequest);

    Result<Integer> collectTerminalData(TerminalDataListRequest terminalDataListRequest);

    Result flowTransformation(TerminalDataReq terminalDataReq);

    public void handleAddData(TerminalDataReq terminalDataReq);

}
