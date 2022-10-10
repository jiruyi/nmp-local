package com.matrictime.network.response;

import com.matrictime.network.modelVo.StaticRouteVo;
import lombok.Data;

import java.util.List;

/**
 * @author by wangqiang
 * @date 2022/10/9.
 */
@Data
public class StaticRouteResponse {
    private List<StaticRouteVo> list;
}
