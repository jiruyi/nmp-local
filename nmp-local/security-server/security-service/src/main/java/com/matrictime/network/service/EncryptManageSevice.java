package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.EncryptConfVo;
import com.matrictime.network.request.QueryKeyDataReq;
import com.matrictime.network.request.UpdEncryptConfReq;
import com.matrictime.network.resp.QueryKeyDataResp;

public interface EncryptManageSevice {

    Result updEncryptConf(UpdEncryptConfReq req);

    Result<EncryptConfVo> queryEncryptConf();

    Result<QueryKeyDataResp> queryKeyData(QueryKeyDataReq req);

    Result flushKey();
}
