package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.CenterConfigVo;
import com.matrictime.network.request.EditConfigReq;
import com.matrictime.network.response.UpdateConfigResp;

import java.util.List;

public interface ConfigService {

    /**
     * 配置同步
     * @param req
     * @return
     */
//    Result syncConfig(EditConfigReq req);

    void initInfo(List<CenterConfigVo> centerConfigVos);

    Result<UpdateConfigResp> update(List<CenterConfigVo> centerConfigVos);

}
