package com.matrictime.network.resp;

import com.matrictime.network.modelVo.DnsManageVo;
import lombok.Data;

import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/11/2.
 */
@Data
public class DnsManageResp {

    private List<DnsManageVo> list;
}
