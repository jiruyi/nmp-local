package com.matrictime.network.response;

import com.matrictime.network.modelVo.LinkRelationVo;

import java.util.List;

public class LinkRelationResponse extends BaseResponse {
    private List<LinkRelationVo> linkRelationVoList;

    public List<LinkRelationVo> getLinkRelationVoList() {
        return linkRelationVoList;
    }

    public void setLinkRelationVoList(List<LinkRelationVo> linkRelationVoList) {
        this.linkRelationVoList = linkRelationVoList;
    }
}