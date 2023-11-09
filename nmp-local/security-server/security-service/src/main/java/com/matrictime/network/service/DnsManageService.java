package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.DnsManageVo;
import com.matrictime.network.modelVo.PageInfo;
import com.matrictime.network.req.CaManageRequest;
import com.matrictime.network.req.DnsManageRequest;
import com.matrictime.network.resp.CaManageResp;
import com.matrictime.network.resp.DnsManageResp;

/**
 * @author by wangqiang
 * @date 2023/11/3.
 */
public interface DnsManageService {

    Result<Integer> insertDnsManage(DnsManageRequest dnsManageRequest);

    Result<Integer> deleteDnsManage(DnsManageRequest dnsManageRequest);

    Result<PageInfo<DnsManageVo>> selectDnsManage(DnsManageRequest dnsManageRequest);

    Result<Integer> updateDnsManage(DnsManageRequest dnsManageRequest);
}
