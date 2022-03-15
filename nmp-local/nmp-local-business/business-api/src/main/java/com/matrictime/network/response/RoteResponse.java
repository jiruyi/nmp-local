package com.matrictime.network.response;

import com.matrictime.network.modelVo.RouteVo;

import java.util.List;

public class RoteResponse extends BaseResponse{
    private List<RouteVo> roteVoList;

    public List<RouteVo> getRoteVoList() {
        return roteVoList;
    }

    public void setRoteVoList(List<RouteVo> roteVoList) {
        this.roteVoList = roteVoList;
    }
}