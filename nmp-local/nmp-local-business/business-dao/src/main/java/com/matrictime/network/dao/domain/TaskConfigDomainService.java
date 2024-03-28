package com.matrictime.network.dao.domain;

import com.matrictime.network.dao.model.NmplTaskConfig;
import com.matrictime.network.modelVo.TaskConfigVo;
import com.matrictime.network.request.QueryTaskReq;
import com.matrictime.network.response.PageInfo;

import java.util.List;

public interface TaskConfigDomainService {

    PageInfo<NmplTaskConfig> queryByPages(QueryTaskReq req);

    Integer update(List<TaskConfigVo> vos);
}
