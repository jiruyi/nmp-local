package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.*;
import com.matrictime.network.response.*;

public interface VersionService {

    /**
     * 上传版本
     * @param uploadVersionFileReq
     * @return
     */
    Result<Integer> insertVersionFile(UploadVersionFileReq uploadVersionFileReq) throws Exception;

    /**
     * 更新版本
     * @param uploadVersionFileReq
     * @return
     */
    Result<Integer> updateVersionFile(UploadVersionFileReq uploadVersionFileReq);

    /**
     * 删除版本
     * @param uploadVersionFileReq
     * @return
     */
    Result<Integer> deleteVersionFile(UploadVersionFileReq uploadVersionFileReq);

    /**
     * 查询版本文件
     * @param uploadVersionFileReq
     * @return
     */
    Result<VersionFileResponse> selectVersionFile(UploadVersionFileReq uploadVersionFileReq);


























}
