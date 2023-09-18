package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.CenterLinkRelationVo;
import com.matrictime.network.modelVo.CenterRouteVo;
import com.matrictime.network.modelVo.LinkRelationVo;
import com.matrictime.network.modelVo.LinkVo;
import com.matrictime.network.request.AddLinkRelationRequest;
import com.matrictime.network.request.UpdateLinkRelationRequest;

import java.util.List;

public interface LinkRelationService {

//    Result<Integer> addLink(List<LinkVo> list);

//    Result<Integer> updateLink(List<LinkVo> list);

    Result<Integer> updateLink(List<LinkVo> list);

    void initInfo(List<LinkVo> linkVos);
}
