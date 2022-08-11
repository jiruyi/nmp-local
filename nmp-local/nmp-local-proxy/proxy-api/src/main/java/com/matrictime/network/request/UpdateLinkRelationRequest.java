package com.matrictime.network.request;

import com.matrictime.network.modelVo.LinkRelationVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UpdateLinkRelationRequest implements Serializable {


    private static final long serialVersionUID = 3014781264040193376L;
    /**
     * 插入链路信息列表
     */
    private List<LinkRelationVo> list;
}
