package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.DataCollectReq;
import com.matrictime.network.request.TerminalDataReq;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.TerminalDataRequest;
import com.matrictime.network.response.TerminalDataResponse;

/**
 * @author by wangqiang
 * @date 2023/4/19.
 */
public interface TerminalDataService {
    Result<TerminalDataResponse> selectTerminalData(TerminalDataRequest terminalDataRequest);

    Result flowTransformation(TerminalDataReq terminalDataReq);

    public void handleAddData(TerminalDataReq terminalDataReq);

}
