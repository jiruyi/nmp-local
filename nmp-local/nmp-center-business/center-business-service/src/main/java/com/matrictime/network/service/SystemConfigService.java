package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.EditBasicConfigReq;
import com.matrictime.network.request.QueryDictionaryReq;
import com.matrictime.network.response.PageInfo;
import org.springframework.web.multipart.MultipartFile;

public interface SystemConfigService {

    /**
     * 基础配置查询
     * @return
     */
    Result<PageInfo> queryBasicConfig();

    /**
     * 基础配置编辑
     * @param req
     * @return
     */
    Result editBasicConfig(EditBasicConfigReq req);

    /**
     * 字典表查询
     * @param req
     * @return
     */
    Result<PageInfo> queryDictionary(QueryDictionaryReq req);

    Result uploadDictionary( MultipartFile file);
}
