package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.DnsManageVo;

/**
 * @author by wangqiang
 * @date 2023/11/6.
 */
public interface DnsManageService {

    Result<Integer> insertDnsManage(DnsManageVo dnsManageVo);

    Result<Integer> deleteDnsManage(DnsManageVo dnsManageVo);
}
