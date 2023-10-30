package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.IpManageRequest;
import com.matrictime.network.resp.IpManageResp;

/**
 * @author by wangqiang
 * @date 2023/10/27.
 */
public interface IpManageService {

    Result<Integer> insertIpManage(IpManageRequest ipManageRequest);

    Result<IpManageResp> selectIpManage(IpManageRequest ipManageRequest);

    Result<Integer> updateIpManage(IpManageRequest ipManageRequest);

    Result<Integer> deleteIpManage(IpManageRequest ipManageRequest);
}
