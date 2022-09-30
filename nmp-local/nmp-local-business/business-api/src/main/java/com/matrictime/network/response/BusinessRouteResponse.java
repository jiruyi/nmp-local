package com.matrictime.network.response;

import com.matrictime.network.modelVo.BusinessRouteVo;

import java.util.List;

/**
 * @author by wangqiang
 * @date 2022/9/28.
 */
public class BusinessRouteResponse extends BaseResponse{

    private List<BusinessRouteVo> list;

    public List<BusinessRouteVo> getList() {
        return list;
    }

    public void setList(List<BusinessRouteVo> list) {
        this.list = list;
    }
}
