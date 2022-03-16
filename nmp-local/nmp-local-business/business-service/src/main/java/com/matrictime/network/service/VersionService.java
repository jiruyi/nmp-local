package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.*;
import com.matrictime.network.response.*;

public interface VersionService {

    /**
     * 编辑版本信息
     * @param req
     * @return
     */
    Result<EditVersionResp> editVersion(EditVersionReq req);

    /**
     * 上传版本文件
     * @param req
     * @return
     */
    Result<UploadVersionFileResp> uploadVersionFile(UploadVersionFileReq req);

    /**
     * 删除版本文件
     * @param req
     * @return
     */
    Result<DeleteVersionFileResp> deleteVersionFile(DeleteVersionFileReq req);

    /**
     * 根据系统查询版本文件列表
     * @param req
     * @return
     */
    Result<QueryVersionFileResp> queryVersionFile(QueryVersionFileReq req);

    /**
     * 查询推送设备/详情
     * @param req
     * @return
     */
    Result<QueryVersionFileDetailResp> queryVersionFileDetail(QueryVersionFileDetailReq req);

    /**
     * 推送设备
     * @param req
     * @return
     */
    Result<PushVersionFileResp> pushVersionFile(PushVersionFileReq req);

    /**
     * 启动版本文件
     * @param req
     * @return
     */
    Result<StartVersionFileResp> startVersionFile(StartVersionFileReq req);
}
