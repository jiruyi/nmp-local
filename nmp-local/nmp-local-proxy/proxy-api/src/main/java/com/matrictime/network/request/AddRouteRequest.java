package com.matrictime.network.request;

import com.matrictime.network.modelVo.RouteVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AddRouteRequest implements Serializable {

    private static final long serialVersionUID = -8842089593309557959L;

    /**
     * 插入路由信息列表
     */
    private List<RouteVo> list;
}
