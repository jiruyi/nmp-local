package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.EncryptConfVo;
import com.matrictime.network.request.UpdEncryptConfReq;

public interface EncryptManageSevice {

    Result updEncryptConf(UpdEncryptConfReq req);

    Result<EncryptConfVo> queryEncryptConf();


}