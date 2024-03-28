package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.EditTaskReq;
import com.matrictime.network.request.QueryTaskReq;
import com.matrictime.network.response.EditTaskResp;
import com.matrictime.network.response.PageInfo;

public interface TaskConfigService {

    /**
     * 分页查询
     * @param req
     * @return
     */
    Result<PageInfo> queryTaskByPages(QueryTaskReq req);

    /**
     * 编辑
     * @param req
     * @return
     */
    Result<EditTaskResp> edit(EditTaskReq req);
}
