package com.matrictime.network.response;

import com.matrictime.network.modelVo.CompanyHeartbeatVo;
import lombok.Data;

import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/9/12.
 */
@Data
public class CompanyHeartbeatResponse {

    private List<CompanyHeartbeatVo> list;
}
