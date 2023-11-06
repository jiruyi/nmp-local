package com.matrictime.network.dao.mapper.extend;

import com.matrictime.network.modelVo.DnsManageVo;
import com.matrictime.network.req.DnsManageRequest;

import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/11/3.
 */
public interface NmpsDnsManageExtMapper {

    List<DnsManageVo> selectDnsManage(DnsManageRequest dnsManageRequest);
}
