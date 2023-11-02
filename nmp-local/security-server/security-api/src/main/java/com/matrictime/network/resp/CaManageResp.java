package com.matrictime.network.resp;

import com.matrictime.network.modelVo.CaManageVo;
import lombok.Data;

import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/11/2.
 */
@Data
public class CaManageResp {

    private List<CaManageVo> list;
}
