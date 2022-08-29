package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.LinkRelationVo;
import com.matrictime.network.request.AddLinkRelationRequest;
import com.matrictime.network.request.UpdateLinkRelationRequest;

import java.util.List;

public interface LinkRelationService {

    Result<Integer> addLinkRelation(List<LinkRelationVo> list);

    Result<Integer> updateLinkRelation(List<LinkRelationVo> list);
}