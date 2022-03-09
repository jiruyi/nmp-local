package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.*;
import com.matrictime.network.response.*;

public interface SignalService {

    /**
     * 配置编辑
     * @param req
     * @return
     */
    Result<EditSignalResp> editSignal(EditSignalReq req);

    /**
     * 信令追踪启停
     * @param req
     * @return
     */
    Result<SignalIoResp> signalIo(SignalIoReq req);

    /**
     * 信令清空
     * @param req
     * @return
     */
    Result<CleanSignalResp> cleanSignal(CleanSignalReq req);


    /**
     * 分页查询信令
     * @param req
     * @return
     */
    Result<QuerySignalByPageResp> querySignalByPage(QuerySignalByPageReq req);

    /**
     * 信令导出
     * @param req
     * @return
     */
    Result<ExportSignalResp> exportSignal(ExportSignalReq req);


}
