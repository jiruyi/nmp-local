package com.matrictime.network.response;

import com.matrictime.network.modelVo.LinkVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class QueryLinkResp implements Serializable {

    private static final long serialVersionUID = 8727729447880488834L;

    /**
     * 链路列表信息
     */
    private List<LinkVo> linkVos;
}
