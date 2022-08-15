package com.matrictime.network.request;

import com.matrictime.network.modelVo.LinkRelationVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AddLinkRelationRequest implements Serializable {

    private static final long serialVersionUID = -4969893891637955548L;

    /**
     * 插入链路信息列表
     */
    private List<LinkRelationVo> list;
}
