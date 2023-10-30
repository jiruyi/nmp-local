package com.matrictime.network.resp;

import com.matrictime.network.modelVo.IpManageVo;
import lombok.Data;

import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/10/27.
 */
@Data
public class IpManageResp {

    private List<IpManageVo> list;
}
