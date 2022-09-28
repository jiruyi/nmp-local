package com.matrictime.network.dao.domain;

import com.matrictime.network.request.OutlinePcListRequest;
import com.matrictime.network.request.OutlinePcReq;

/**
 * @author by wangqiang
 * @date 2022/9/15.
 */
public interface OutlinePcDomainService {

    int updateOutlinePc(OutlinePcReq outlinePcReq);

    int batchInsertOutlinePc(OutlinePcListRequest listRequest);
}
