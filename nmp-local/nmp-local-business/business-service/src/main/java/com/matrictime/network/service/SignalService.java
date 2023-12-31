package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.NmplSignalVo;
import com.matrictime.network.request.*;
import com.matrictime.network.response.*;

public interface SignalService {

    /**
     * 信令编辑
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
     * 信令上报
     * @param req
     * @return
     */
    Result addSignal(AddSignalReq req);

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
    Result<PageInfo> querySignalByPage(QuerySignalByPageReq req);

    /**
     * 根据userId查询设备列表
     * @param req
     * @return
     */
    Result<QuerySignalSelectDeviceIdsResp> querySignalSelectDeviceIds(QuerySignalSelectDeviceIdsReq req);

    /**
     * 信令导出
     * @param req
     * @return
     */
    Result<ExportSignalResp> exportSignal(ExportSignalReq req);


    Result<SelectDevicesForSignalResp> selectDevicesForSignal();


}
