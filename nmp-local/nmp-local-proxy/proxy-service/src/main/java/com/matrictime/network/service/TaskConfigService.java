package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.CenterTaskConfigVo;
import com.matrictime.network.response.UpdateTaskConfigResp;

import java.util.List;

public interface TaskConfigService {

    void initInfo(List<CenterTaskConfigVo> centerTaskConfigVos);

    Result<UpdateTaskConfigResp> update(List<CenterTaskConfigVo> centerTaskConfigVos);
}
