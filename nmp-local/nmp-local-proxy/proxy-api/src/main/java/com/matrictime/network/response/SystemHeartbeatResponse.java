package com.matrictime.network.response;

import com.matrictime.network.modelVo.SystemHeartbeatVo;
import lombok.Data;

import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/4/18.
 */
@Data
public class SystemHeartbeatResponse {
    private List<SystemHeartbeatVo> list;

}
